package com.Cultivation.xuanhuancraft.Gameplay;

import com.Cultivation.xuanhuancraft.DataStructures.AxialCordinate;
import com.Cultivation.xuanhuancraft.DataStructures.Cultivation;
import com.Cultivation.xuanhuancraft.DataStructures.CultivationTile;
import com.mojang.logging.LogUtils;
import org.slf4j.Logger;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class HexGridGame
{
    private static final Logger LOGGER = LogUtils.getLogger();
    private static final int[][] NEIGHBORS = new int[][]{
            {1, 0}, {1, -1}, {0, -1}, {-1, 0}, {-1, 1}, {0, 1}
    };

    private final Cultivation cultivation;
    private final Map<AxialCordinate, List<EnergyPacket>> packets = new HashMap<>();

    public HexGridGame(Cultivation cultivation)
    {
        this.cultivation = cultivation;
    }

    public void tick()
    {
        Map<AxialCordinate, CultivationTile> tileIndex = buildTileIndex();
        AxialCordinate sink = findSink(tileIndex).orElse(null);

        spawnFromSources(tileIndex);

        Map<AxialCordinate, List<EnergyPacket>> nextPackets = new HashMap<>();
        Map<AxialCordinate, List<EnergyPacket>> waitingForFusion = new HashMap<>();
        double scoreToAdd = 0.0;

        for (Map.Entry<AxialCordinate, List<EnergyPacket>> entry : packets.entrySet())
        {
            AxialCordinate coordinate = entry.getKey();
            CultivationTile tile = tileIndex.get(coordinate);
            TileType type = tile != null ? tile.getType() : TileType.PATH;
            for (EnergyPacket packet : entry.getValue())
            {
                packet = applyTileEffect(tile, packet);
                if (type == TileType.SINK)
                {
                    scoreToAdd += calculateScore(packet);
                    continue;
                }

                if (type == TileType.FUSION)
                {
                    waitingForFusion.computeIfAbsent(coordinate, key -> new ArrayList<>()).add(packet);
                    continue;
                }

                AxialCordinate target = chooseNextStep(coordinate, sink, tileIndex);
                if (target == null)
                {
                    addPacket(nextPackets, coordinate, packet);
                }
                else
                {
                    addPacket(nextPackets, target, packet);
                }
            }
        }

        handleFusion(waitingForFusion, nextPackets, tileIndex, sink);

        cultivation.Qi += scoreToAdd;
        packets.clear();
        packets.putAll(nextPackets);
    }

    public Map<AxialCordinate, List<EnergyPacket>> getPackets()
    {
        return packets;
    }

    private void spawnFromSources(Map<AxialCordinate, CultivationTile> tileIndex)
    {
        for (Map.Entry<AxialCordinate, CultivationTile> entry : tileIndex.entrySet())
        {
            AxialCordinate coordinate = entry.getKey();
            CultivationTile tile = entry.getValue();
            if (tile != null && tile.getType() == TileType.SOURCE)
            {
                addPacket(packets, coordinate, new EnergyPacket());
            }
        }
    }

    private Map<AxialCordinate, CultivationTile> buildTileIndex()
    {
        Map<AxialCordinate, CultivationTile> index = new HashMap<>();
        for (AxialCordinate coordinate : cultivation.Grid)
        {
            index.put(coordinate, coordinate.getTile());
        }
        return index;
    }

    private Optional<AxialCordinate> findSink(Map<AxialCordinate, CultivationTile> tileIndex)
    {
        return tileIndex.entrySet().stream()
                .filter(entry -> entry.getValue() != null && entry.getValue().getType() == TileType.SINK)
                .map(Map.Entry::getKey)
                .findFirst();
    }

    private AxialCordinate chooseNextStep(AxialCordinate origin, AxialCordinate sink, Map<AxialCordinate, CultivationTile> tileIndex)
    {
        if (sink == null)
        {
            return null;
        }

        AxialCordinate best = null;
        int bestDistance = Integer.MAX_VALUE;
        for (int[] offset : NEIGHBORS)
        {
            AxialCordinate candidate = new AxialCordinate(origin.getX() + offset[0], origin.getY() + offset[1]);
            if (!containsTile(candidate, tileIndex) && !candidate.equals(sink))
            {
                continue;
            }
            int distance = axialDistance(candidate, sink);
            if (distance < bestDistance)
            {
                best = candidate;
                bestDistance = distance;
            }
        }
        return best;
    }

    private boolean containsTile(AxialCordinate coordinate, Map<AxialCordinate, CultivationTile> tileIndex)
    {
        return tileIndex.containsKey(coordinate);
    }

    private int axialDistance(AxialCordinate a, AxialCordinate b)
    {
        int dx = a.getX() - b.getX();
        int dy = a.getY() - b.getY();
        int dz = -dx - dy;
        return (Math.abs(dx) + Math.abs(dy) + Math.abs(dz)) / 2;
    }

    private EnergyPacket applyTileEffect(CultivationTile tile, EnergyPacket packet)
    {
        TileType type = tile != null ? tile.getType() : TileType.PATH;
        switch (type)
        {
            case SEPARATOR -> packet = applySeparator(tile, packet);
            case AMPLIFIER -> packet = applyAmplifier(tile, packet);
            case PURIFIER -> packet.setPurity(packet.getPurity() + 10);
            case EVOLVER -> packet = applyEvolver(tile, packet);
            default -> {}
        }
        return packet;
    }

    private EnergyPacket applySeparator(CultivationTile tile, EnergyPacket packet)
    {
        if (tile == null)
        {
            return packet;
        }

        BaseElement focus = tile.getFocusElement();
        if (focus == null)
        {
            return packet;
        }
        if (packet.contains(focus))
        {
            double adjustedPurity = Math.max(0, packet.getPurity() - 5);
            return EnergyPacket.single(focus, adjustedPurity);
        }
        return packet;
    }

    private EnergyPacket applyAmplifier(CultivationTile tile, EnergyPacket packet)
    {
        if (tile == null)
        {
            packet.setPurity(packet.getPurity() + 5);
            return packet;
        }

        BaseElement focus = tile.getFocusElement();
        if (focus == null)
        {
            packet.setPurity(packet.getPurity() + 5);
            return packet;
        }

        if (packet.contains(focus))
        {
            packet.setPurity(packet.getPurity() + 15);
            if (!packet.isSingleElement() && packet.getPurity() >= 40)
            {
                packet.collapseTo(focus);
            }
        }
        else
        {
            packet.setPurity(packet.getPurity() - 5);
        }
        return packet;
    }

    private EnergyPacket applyEvolver(CultivationTile tile, EnergyPacket packet)
    {
        double threshold = packet.isFusedPair() ? 70 : 60;
        BaseElement focus = tile != null ? tile.getFocusElement() : null;
        if (focus != null && !packet.contains(focus) && !packet.isFusedPair())
        {
            return packet;
        }
        if (packet.getPurity() >= threshold)
        {
            packet.evolve();
            packet.setPurity(packet.getPurity() + 5);
        }
        return packet;
    }

    private void handleFusion(Map<AxialCordinate, List<EnergyPacket>> waitingForFusion, Map<AxialCordinate, List<EnergyPacket>> nextPackets,
                              Map<AxialCordinate, CultivationTile> tileIndex, AxialCordinate sink)
    {
        for (Map.Entry<AxialCordinate, List<EnergyPacket>> entry : waitingForFusion.entrySet())
        {
            List<EnergyPacket> queue = new ArrayList<>(entry.getValue());
            List<EnergyPacket> fusedResults = new ArrayList<>();
            List<EnergyPacket> remaining = new ArrayList<>();

            while (queue.size() >= 2)
            {
                EnergyPacket first = queue.remove(0);
                EnergyPacket second = queue.remove(0);
                if (first.isSingleElement() && second.isSingleElement() && !first.getElements().equals(second.getElements()))
                {
                    BaseElement firstElement = first.getElements().iterator().next();
                    BaseElement secondElement = second.getElements().iterator().next();
                    double purity = (first.getPurity() + second.getPurity()) / 2 + 5;
                    fusedResults.add(EnergyPacket.fused(firstElement, secondElement, purity));
                }
                else
                {
                    remaining.add(first);
                    remaining.add(second);
                }
            }

            remaining.addAll(queue);
            List<EnergyPacket> carried = new ArrayList<>(remaining);
            carried.addAll(fusedResults);

            if (carried.isEmpty())
            {
                continue;
            }

            if (carried.size() == 1)
            {
                EnergyPacket packet = carried.get(0);
                AxialCordinate target = chooseNextStep(entry.getKey(), sink, tileIndex);
                if (target == null)
                {
                    addPacket(nextPackets, entry.getKey(), packet);
                }
                else
                {
                    addPacket(nextPackets, target, packet);
                }
            }
            else
            {
                // Keep multiple packets on the fusion tile so they can merge with future arrivals.
                for (EnergyPacket packet : carried)
                {
                    addPacket(nextPackets, entry.getKey(), packet);
                }
            }
        }
    }

    private double calculateScore(EnergyPacket packet)
    {
        double multiplier = switch (packet.getTier())
        {
            case BASE -> 1.0;
            case FUSED -> 1.5;
            case EVOLVED -> 2.0;
        };
        return packet.getPurity() * multiplier;
    }

    private void addPacket(Map<AxialCordinate, List<EnergyPacket>> target, AxialCordinate coordinate, EnergyPacket packet)
    {
        target.computeIfAbsent(coordinate, key -> new ArrayList<>()).add(packet);
    }
}
