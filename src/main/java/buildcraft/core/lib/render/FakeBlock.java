/*    */ package buildcraft.core.lib.render;
/*    */ 
/*    */ import buildcraft.api.core.render.ITextureStateManager;
/*    */ import buildcraft.api.core.render.ITextureStates;
/*    */ import cpw.mods.fml.relauncher.Side;
/*    */ import cpw.mods.fml.relauncher.SideOnly;
/*    */ import net.minecraft.block.Block;
/*    */ import net.minecraft.block.material.Material;
/*    */ import net.minecraft.util.IIcon;
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
/*    */ 
/*    */ 
/*    */ @SideOnly(Side.CLIENT)
/*    */ public final class FakeBlock
/*    */   extends Block
/*    */   implements ITextureStates
/*    */ {
/* 29 */   public static final FakeBlock INSTANCE = new FakeBlock();
/*    */   
/* 31 */   private int renderMask = 0;
/* 32 */   private int colorMultiplier = 16777215;
/*    */   
/*    */   private TextureStateManager textureState;
/*    */   
/*    */   private FakeBlock() {
/* 37 */     super(Material.field_151592_s);
/* 38 */     this.textureState = new TextureStateManager(null);
/*    */   }
/*    */ 
/*    */   
/*    */   public int func_149720_d(IBlockAccess blockAccess, int x, int y, int z) {
/* 43 */     return this.colorMultiplier;
/*    */   }
/*    */   
/*    */   @Deprecated
/*    */   public int getColor() {
/* 48 */     return this.colorMultiplier;
/*    */   }
/*    */ 
/*    */   
/*    */   public int func_149635_D() {
/* 53 */     return this.colorMultiplier;
/*    */   }
/*    */   
/*    */   public void setColor(int color) {
/* 57 */     this.colorMultiplier = color;
/*    */   }
/*    */ 
/*    */   
/*    */   public TextureStateManager getTextureState() {
/* 62 */     return this.textureState;
/*    */   }
/*    */ 
/*    */   
/*    */   public IIcon func_149691_a(int side, int meta) {
/* 67 */     return this.textureState.isSided() ? this.textureState.getTextureArray()[side] : this.textureState.getTexture();
/*    */   }
/*    */ 
/*    */   
/*    */   public void setRenderSide(ForgeDirection side, boolean render) {
/* 72 */     if (render) {
/* 73 */       this.renderMask |= 1 << side.ordinal();
/*    */     } else {
/* 75 */       this.renderMask &= 1 << side.ordinal() ^ 0xFFFFFFFF;
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public void setRenderAllSides() {
/* 81 */     this.renderMask = 63;
/*    */   }
/*    */ 
/*    */   
/*    */   public void setRenderMask(int mask) {
/* 86 */     this.renderMask = mask;
/*    */   }
/*    */ 
/*    */   
/*    */   public Block getBlock() {
/* 91 */     return this;
/*    */   }
/*    */ 
/*    */   
/*    */   @SideOnly(Side.CLIENT)
/*    */   public boolean func_149646_a(IBlockAccess blockAccess, int x, int y, int z, int side) {
/* 97 */     return ((this.renderMask & 1 << side) != 0);
/*    */   }
/*    */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-core.jar!\buildcraft\core\lib\render\FakeBlock.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */