package com.jship.oritechsouls.capabilities;

import com.buuz135.industrialforegoingsouls.capabilities.ISoulHandler;
import com.jship.oritechsouls.OritechSouls;

import net.minecraft.core.Direction;
import rearth.oritech.block.entity.arcane.EnchantmentCatalystBlockEntity;

public class CatalystSoulCapability implements ISoulHandler {

    private final EnchantmentCatalystBlockEntity catalystEntity;
    private final Direction direction;

    public CatalystSoulCapability(EnchantmentCatalystBlockEntity catalystEntity, Direction direction) {
        this.catalystEntity = catalystEntity;
        this.direction = direction;
    }

    @Override
    public int getSoulTanks() {
        return 1;
    }

    @Override
    public int getSoulInTank(int tank) {
        return catalystEntity.collectedSouls;
    }

    @Override
    public int drain(int maxDrain, Action action) {
        // only drain from the bottom
        if (direction != Direction.DOWN) {
            return 0;
        }
        if (action.execute()) {
            var oldAmount = catalystEntity.collectedSouls;
            catalystEntity.collectedSouls = Math.max(0, catalystEntity.collectedSouls - maxDrain);
            try {
                var networkDirty = catalystEntity.getClass().getDeclaredField("networkDirty");
                networkDirty.setAccessible(true);
                networkDirty.setBoolean(catalystEntity, true);
            } catch(Exception e) {
                OritechSouls.LOGGER.error("Unable to set networkDirty for EnchantmentCatalystBlockEntity");
            }
            return oldAmount - catalystEntity.collectedSouls;
        } else {
            var oldAmount = catalystEntity.collectedSouls;
            var newAmount = Math.max(0, catalystEntity.collectedSouls - maxDrain);
            return oldAmount - newAmount;
        }
    }

    @Override
    public int fill(int maxFill, Action action) {
        // do not fill from the bottom
        if (direction == Direction.DOWN) {
            return 0;
        }
        if (action.execute()) {
            var oldAmount = catalystEntity.collectedSouls;
            catalystEntity.collectedSouls = Math.min(catalystEntity.maxSouls, catalystEntity.collectedSouls + maxFill);
            try {
                var networkDirty = catalystEntity.getClass().getDeclaredField("networkDirty");
                networkDirty.setAccessible(true);
                networkDirty.setBoolean(catalystEntity, true);
            } catch(Exception e) {
                OritechSouls.LOGGER.error("Unable to set networkDirty for EnchantmentCatalystBlockEntity");
            }
            return catalystEntity.collectedSouls - oldAmount;
        } else {
            var oldAmount = catalystEntity.collectedSouls;
            var newAmount = Math.min(catalystEntity.maxSouls, catalystEntity.collectedSouls + maxFill);
            return newAmount - oldAmount;
        }
    }

    @Override
    public int getTankCapacity(int arg0) {
        return catalystEntity.maxSouls;
    }   
}
