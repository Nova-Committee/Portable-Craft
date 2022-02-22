package committee.nova.portablecraft.core;

import net.minecraft.entity.player.PlayerEntity;

/**
 * Description:
 * Author: cnlimiter
 * Date: 2022/2/21 13:51
 * Version: 1.0
 */
public class PlayerSpawnData {
    private final PlayerEntity player;
    private long BedLocate = 0L;
    private boolean inBed = false;

    public PlayerSpawnData(PlayerEntity player, long BedLocate) {
        this.player = player;
        this.BedLocate = BedLocate;
    }

    public long getBedLocate() {
        return this.BedLocate;
    }

    public boolean isPlayer(PlayerEntity player) {
        return this.player.getUUID() == player.getUUID();
    }

    public PlayerEntity getPlayer() {
        return this.player;
    }

    public boolean isBed() {
        return this.inBed;
    }

    public void setBed(boolean sleeping) {
        this.inBed = sleeping;
    }
}
