/*    */ package buildcraft.api.robots;
/*    */ 
/*    */ import buildcraft.api.boards.RedstoneBoardRobot;
/*    */ import buildcraft.api.core.IFluidHandlerAdv;
/*    */ import buildcraft.api.core.IZone;
/*    */ import buildcraft.api.mj.MjAPI;
/*    */ import buildcraft.api.mj.MjBattery;
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.entity.EntityLiving;
/*    */ import net.minecraft.item.ItemStack;
/*    */ import net.minecraft.tileentity.TileEntity;
/*    */ import net.minecraft.util.math.BlockPos;
/*    */ import net.minecraft.world.World;
/*    */ import net.minecraftforge.items.IItemHandler;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public abstract class EntityRobotBase
/*    */   extends EntityLiving
/*    */   implements IItemHandler, IFluidHandlerAdv
/*    */ {
/* 24 */   public static final long MAX_POWER = 5000L * MjAPI.MJ;
/* 25 */   public static final long SAFETY_POWER = MAX_POWER / 5L;
/*    */   public static final long SHUTDOWN_POWER = 0L;
/*    */   public static final long NULL_ROBOT_ID = 9223372036854775807L;
/*    */   
/*    */   public EntityRobotBase(World par1World) {
/* 30 */     super(par1World);
/*    */   }
/*    */   
/*    */   public abstract void setItemInUse(ItemStack paramItemStack);
/*    */   
/*    */   public abstract void setItemActive(boolean paramBoolean);
/*    */   
/*    */   public abstract boolean isMoving();
/*    */   
/*    */   public abstract DockingStation getLinkedStation();
/*    */   
/*    */   public abstract RedstoneBoardRobot getBoard();
/*    */   
/*    */   public abstract void aimItemAt(float paramFloat1, float paramFloat2);
/*    */   
/*    */   public abstract void aimItemAt(BlockPos paramBlockPos);
/*    */   
/*    */   public abstract float getAimYaw();
/*    */   
/*    */   public abstract float getAimPitch();
/*    */   
/*    */   public long getPower() {
/* 52 */     return getBattery().getStored();
/*    */   }
/*    */   
/*    */   public abstract MjBattery getBattery();
/*    */   
/*    */   public abstract DockingStation getDockingStation();
/*    */   
/*    */   public abstract void dock(DockingStation paramDockingStation);
/*    */   
/*    */   public abstract void undock();
/*    */   
/*    */   public abstract IZone getZoneToWork();
/*    */   
/*    */   public abstract IZone getZoneToLoadUnload();
/*    */   
/*    */   public abstract boolean containsItems();
/*    */   
/*    */   public abstract boolean hasFreeSlot();
/*    */   
/*    */   public abstract void unreachableEntityDetected(Entity paramEntity);
/*    */   
/*    */   public abstract boolean isKnownUnreachable(Entity paramEntity);
/*    */   
/*    */   public abstract long getRobotId();
/*    */   
/*    */   public abstract IRobotRegistry getRegistry();
/*    */   
/*    */   public abstract void releaseResources();
/*    */   
/*    */   public abstract void onChunkUnload();
/*    */   
/*    */   public abstract ItemStack receiveItem(TileEntity paramTileEntity, ItemStack paramItemStack);
/*    */   
/*    */   public abstract void setMainStation(DockingStation paramDockingStation);
/*    */ }


/* Location:              C:\Users\Nikit\files\buildcraft-api-7.99.8.2.jar!\buildcraft\api\robots\EntityRobotBase.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */