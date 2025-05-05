/*    */ package buildcraft.core;
/*    */ 
/*    */ import buildcraft.api.transport.IInjectable;
/*    */ import cpw.mods.fml.common.Loader;
/*    */ import net.minecraft.block.Block;
/*    */ import net.minecraft.tileentity.TileEntity;
/*    */ import net.minecraft.world.IBlockAccess;
/*    */ import net.minecraftforge.common.util.ForgeDirection;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class CompatHooks
/*    */ {
/*    */   public static final CompatHooks INSTANCE;
/*    */   
/*    */   static {
/* 24 */     CompatHooks i = null;
/* 25 */     if (Loader.isModLoaded("BuildCraft|Compat")) {
/*    */       try {
/* 27 */         i = (CompatHooks)CompatHooks.class.getClassLoader().loadClass("buildcraft.compat.CompatHooksImpl").newInstance();
/* 28 */       } catch (Exception e) {
/* 29 */         e.printStackTrace();
/*    */       } 
/*    */     }
/*    */     
/* 33 */     if (i == null) {
/* 34 */       i = new CompatHooks();
/*    */     }
/*    */     
/* 37 */     INSTANCE = i;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public IInjectable getInjectableWrapper(TileEntity tile, ForgeDirection side) {
/* 45 */     return null;
/*    */   }
/*    */   
/*    */   public Block getVisualBlock(IBlockAccess world, int x, int y, int z, ForgeDirection side) {
/* 49 */     return null;
/*    */   }
/*    */   
/*    */   public int getVisualMeta(IBlockAccess world, int x, int y, int z, ForgeDirection side) {
/* 53 */     return -1;
/*    */   }
/*    */   
/*    */   public Block getBlock(Class<? extends Block> klazz) {
/* 57 */     Block block = null;
/*    */     
/* 59 */     if (Loader.isModLoaded("BuildCraft|Compat")) {
/*    */       try {
/* 61 */         block = (Block)CompatHooks.class.getClassLoader().loadClass(klazz.getName() + "Compat").newInstance();
/* 62 */       } catch (ClassNotFoundException classNotFoundException) {
/*    */       
/* 64 */       } catch (Exception e) {
/* 65 */         e.printStackTrace();
/*    */       } 
/*    */     }
/*    */     
/* 69 */     if (block == null) {
/*    */       try {
/* 71 */         block = klazz.newInstance();
/* 72 */       } catch (Exception e) {
/* 73 */         e.printStackTrace();
/*    */       } 
/*    */     }
/*    */     
/* 77 */     return block;
/*    */   }
/*    */   
/*    */   public Class<? extends TileEntity> getTile(Class<? extends TileEntity> klazz) {
/* 81 */     Class<? extends TileEntity> tileClass = klazz;
/*    */     
/* 83 */     if (Loader.isModLoaded("BuildCraft|Compat")) {
/*    */       try {
/* 85 */         tileClass = (Class)CompatHooks.class.getClassLoader().loadClass(klazz.getName() + "Compat");
/* 86 */       } catch (ClassNotFoundException e) {
/*    */         
/* 88 */         tileClass = klazz;
/* 89 */       } catch (Exception e) {
/* 90 */         e.printStackTrace();
/*    */       } 
/*    */     }
/*    */     
/* 94 */     return tileClass;
/*    */   }
/*    */   
/*    */   public Object getEnergyProvider(TileEntity tile) {
/* 98 */     return tile;
/*    */   }
/*    */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-core.jar!\buildcraft\core\CompatHooks.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */