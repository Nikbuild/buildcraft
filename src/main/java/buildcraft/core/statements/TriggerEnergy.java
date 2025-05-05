/*     */ package buildcraft.core.statements;
/*     */ 
/*     */ import buildcraft.api.statements.IStatementContainer;
/*     */ import buildcraft.api.statements.IStatementParameter;
/*     */ import buildcraft.api.statements.ITriggerInternal;
/*     */ import buildcraft.api.transport.IPipeTile;
/*     */ import buildcraft.core.lib.utils.StringUtils;
/*     */ import cofh.api.energy.IEnergyConnection;
/*     */ import cofh.api.energy.IEnergyHandler;
/*     */ import cofh.api.energy.IEnergyProvider;
/*     */ import cofh.api.energy.IEnergyReceiver;
/*     */ import cpw.mods.fml.relauncher.Side;
/*     */ import cpw.mods.fml.relauncher.SideOnly;
/*     */ import net.minecraft.client.renderer.texture.IIconRegister;
/*     */ import net.minecraft.tileentity.TileEntity;
/*     */ import net.minecraftforge.common.util.ForgeDirection;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class TriggerEnergy
/*     */   extends BCStatement
/*     */   implements ITriggerInternal
/*     */ {
/*     */   private final boolean high;
/*     */   
/*     */   public static class Neighbor
/*     */   {
/*     */     public TileEntity tile;
/*     */     public ForgeDirection side;
/*     */     
/*     */     public Neighbor(TileEntity tile, ForgeDirection side) {
/*  34 */       this.tile = tile;
/*  35 */       this.side = side;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public TriggerEnergy(boolean high) {
/*  42 */     super(new String[] { "buildcraft:energyStored" + (high ? "high" : "low") });
/*     */     
/*  44 */     this.high = high;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getDescription() {
/*  49 */     return StringUtils.localize("gate.trigger.machine.energyStored." + (this.high ? "high" : "low"));
/*     */   }
/*     */   
/*     */   private boolean isTriggeredEnergyHandler(IEnergyConnection connection, ForgeDirection side) {
/*     */     int energyStored;
/*     */     int energyMaxStored;
/*  55 */     if (connection instanceof IEnergyHandler) {
/*  56 */       energyStored = ((IEnergyHandler)connection).getEnergyStored(side);
/*  57 */       energyMaxStored = ((IEnergyHandler)connection).getMaxEnergyStored(side);
/*  58 */     } else if (connection instanceof IEnergyProvider) {
/*  59 */       energyStored = ((IEnergyProvider)connection).getEnergyStored(side);
/*  60 */       energyMaxStored = ((IEnergyProvider)connection).getMaxEnergyStored(side);
/*  61 */     } else if (connection instanceof IEnergyReceiver) {
/*  62 */       energyStored = ((IEnergyReceiver)connection).getEnergyStored(side);
/*  63 */       energyMaxStored = ((IEnergyReceiver)connection).getMaxEnergyStored(side);
/*     */     } else {
/*  65 */       return false;
/*     */     } 
/*     */     
/*  68 */     if (energyMaxStored > 0) {
/*  69 */       float level = energyStored / energyMaxStored;
/*  70 */       if (this.high) {
/*  71 */         return (level > 0.95F);
/*     */       }
/*  73 */       return (level < 0.05F);
/*     */     } 
/*     */     
/*  76 */     return false;
/*     */   }
/*     */   
/*     */   protected static boolean isTriggered(Object tile, ForgeDirection side) {
/*  80 */     return ((tile instanceof IEnergyHandler || tile instanceof IEnergyProvider || tile instanceof IEnergyReceiver) && ((IEnergyConnection)tile)
/*  81 */       .canConnectEnergy(side.getOpposite()));
/*     */   }
/*     */   
/*     */   protected boolean isActive(Object tile, ForgeDirection side) {
/*  85 */     if (isTriggered(tile, side)) {
/*  86 */       return isTriggeredEnergyHandler((IEnergyConnection)tile, side.getOpposite());
/*     */     }
/*     */     
/*  89 */     return false;
/*     */   }
/*     */   
/*     */   public static boolean isTriggeringPipe(TileEntity tile) {
/*  93 */     if (tile instanceof IPipeTile) {
/*  94 */       IPipeTile pipeTile = (IPipeTile)tile;
/*  95 */       if (pipeTile.getPipeType() == IPipeTile.PipeType.POWER && pipeTile.getPipe() instanceof IEnergyHandler) {
/*  96 */         return true;
/*     */       }
/*     */     } 
/*  99 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   @SideOnly(Side.CLIENT)
/*     */   public void registerIcons(IIconRegister iconRegister) {
/* 105 */     this.icon = iconRegister.func_94245_a("buildcraftcore:triggers/trigger_energy_storage_" + (this.high ? "high" : "low"));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isTriggerActive(IStatementContainer source, IStatementParameter[] parameters) {
/* 111 */     if (isTriggeringPipe(source.getTile())) {
/* 112 */       return isActive(((IPipeTile)source.getTile()).getPipe(), ForgeDirection.UNKNOWN);
/*     */     }
/*     */     
/* 115 */     Neighbor triggeringNeighbor = getTriggeringNeighbor(source.getTile());
/* 116 */     if (triggeringNeighbor != null) {
/* 117 */       return isActive(triggeringNeighbor.tile, triggeringNeighbor.side);
/*     */     }
/* 119 */     return false;
/*     */   }
/*     */   
/*     */   public static Neighbor getTriggeringNeighbor(TileEntity parent) {
/* 123 */     if (parent instanceof IPipeTile) {
/* 124 */       for (ForgeDirection side : ForgeDirection.VALID_DIRECTIONS) {
/* 125 */         TileEntity tile = ((IPipeTile)parent).getNeighborTile(side);
/* 126 */         if (tile != null && isTriggered(tile, side)) {
/* 127 */           return new Neighbor(tile, side);
/*     */         }
/*     */       } 
/*     */     } else {
/* 131 */       for (ForgeDirection side : ForgeDirection.VALID_DIRECTIONS) {
/* 132 */         TileEntity tile = parent.func_145831_w().func_147438_o(parent.field_145851_c + side.offsetX, parent.field_145848_d + side.offsetY, parent.field_145849_e + side.offsetZ);
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 137 */         if (tile != null && isTriggered(tile, side)) {
/* 138 */           return new Neighbor(tile, side);
/*     */         }
/*     */       } 
/*     */     } 
/* 142 */     return null;
/*     */   }
/*     */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-core.jar!\buildcraft\core\statements\TriggerEnergy.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */