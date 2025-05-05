/*    */ package buildcraft.core;
/*    */ 
/*    */ import buildcraft.core.lib.engines.BlockEngineBase;
/*    */ import buildcraft.core.lib.engines.TileEngineBase;
/*    */ import java.util.List;
/*    */ import net.minecraft.block.Block;
/*    */ import net.minecraft.creativetab.CreativeTabs;
/*    */ import net.minecraft.item.Item;
/*    */ import net.minecraft.item.ItemStack;
/*    */ import net.minecraft.tileentity.TileEntity;
/*    */ import net.minecraft.world.World;
/*    */ 
/*    */ public class BlockEngine
/*    */   extends BlockEngineBase
/*    */ {
/*    */   private final Class[] engineTiles;
/*    */   private final String[] names;
/*    */   private final String[] texturePaths;
/*    */   
/*    */   public BlockEngine() {
/* 21 */     func_149663_c("engineBlock");
/*    */     
/* 23 */     this.engineTiles = new Class[16];
/* 24 */     this.names = new String[16];
/* 25 */     this.texturePaths = new String[16];
/*    */   }
/*    */ 
/*    */   
/*    */   public String getTexturePrefix(int meta, boolean addPrefix) {
/* 30 */     if (this.texturePaths[meta] != null) {
/* 31 */       if (addPrefix) {
/* 32 */         return this.texturePaths[meta].replaceAll(":", ":textures/blocks/");
/*    */       }
/* 34 */       return this.texturePaths[meta];
/*    */     } 
/*    */     
/* 37 */     return null;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public String getUnlocalizedName(int metadata) {
/* 43 */     return (this.names[metadata] != null) ? this.names[metadata] : "unknown";
/*    */   }
/*    */   
/*    */   public void registerTile(Class<? extends TileEngineBase> engineTile, int meta, String name, String texturePath) {
/* 47 */     if (BCRegistry.INSTANCE.isEnabled("engines", name)) {
/* 48 */       this.engineTiles[meta] = engineTile;
/* 49 */       this.names[meta] = name;
/* 50 */       this.texturePaths[meta] = texturePath;
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public TileEntity createTileEntity(World world, int metadata) {
/* 56 */     if (this.engineTiles[metadata] == null) {
/* 57 */       return null;
/*    */     }
/*    */     try {
/* 60 */       return this.engineTiles[metadata].newInstance();
/* 61 */     } catch (Exception e) {
/* 62 */       e.printStackTrace();
/* 63 */       return null;
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void func_149666_a(Item item, CreativeTabs par2CreativeTabs, List<ItemStack> itemList) {
/* 70 */     for (int i = 0; i < 16; i++) {
/* 71 */       if (this.engineTiles[i] != null) {
/* 72 */         itemList.add(new ItemStack((Block)this, 1, i));
/*    */       }
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean hasEngine(int meta) {
/* 79 */     return (this.engineTiles[meta] != null);
/*    */   }
/*    */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-core.jar!\buildcraft\core\BlockEngine.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */