/*     */ package buildcraft.core.statements;
/*     */ 
/*     */ import buildcraft.api.statements.IStatement;
/*     */ import buildcraft.api.statements.IStatementContainer;
/*     */ import buildcraft.api.statements.IStatementParameter;
/*     */ import buildcraft.api.statements.StatementMouseClick;
/*     */ import buildcraft.api.transport.IPipeTile;
/*     */ import buildcraft.core.lib.utils.StringUtils;
/*     */ import net.minecraft.client.renderer.texture.IIconRegister;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.util.IIcon;
/*     */ import net.minecraftforge.common.util.ForgeDirection;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class StatementParameterDirection
/*     */   implements IStatementParameter
/*     */ {
/*     */   private static IIcon[] icons;
/*  29 */   public ForgeDirection direction = ForgeDirection.UNKNOWN;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ItemStack getItemStack() {
/*  37 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public IIcon getIcon() {
/*  42 */     if (this.direction == ForgeDirection.UNKNOWN) {
/*  43 */       return null;
/*     */     }
/*  45 */     return icons[this.direction.ordinal()];
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void onClick(IStatementContainer source, IStatement stmt, ItemStack stack, StatementMouseClick mouse) {
/*  51 */     if (source.getTile() instanceof IPipeTile) {
/*  52 */       for (int i = 0; i < 6; i++) {
/*  53 */         int dir = (this.direction.ordinal() + ((mouse.getButton() > 0) ? -1 : 1)) % 6;
/*  54 */         if (dir < 0) {
/*  55 */           dir += 6;
/*     */         }
/*     */         
/*  58 */         this.direction = ForgeDirection.getOrientation(dir);
/*  59 */         if (((IPipeTile)source.getTile()).isPipeConnected(this.direction)) {
/*     */           return;
/*     */         }
/*     */       } 
/*  63 */       this.direction = ForgeDirection.UNKNOWN;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void writeToNBT(NBTTagCompound nbt) {
/*  69 */     nbt.func_74774_a("direction", (byte)this.direction.ordinal());
/*     */   }
/*     */ 
/*     */   
/*     */   public void readFromNBT(NBTTagCompound nbt) {
/*  74 */     if (nbt.func_74764_b("direction")) {
/*  75 */       this.direction = ForgeDirection.getOrientation(nbt.func_74771_c("direction"));
/*     */     } else {
/*  77 */       this.direction = ForgeDirection.UNKNOWN;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object object) {
/*  83 */     if (object instanceof StatementParameterDirection) {
/*  84 */       StatementParameterDirection param = (StatementParameterDirection)object;
/*  85 */       return (param.direction == this.direction);
/*     */     } 
/*  87 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getDescription() {
/*  92 */     if (this.direction == ForgeDirection.UNKNOWN) {
/*  93 */       return "";
/*     */     }
/*  95 */     return StringUtils.localize("direction." + this.direction.name().toLowerCase());
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String getUniqueTag() {
/* 101 */     return "buildcraft:pipeActionDirection";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void registerIcons(IIconRegister iconRegister) {
/* 112 */     icons = new IIcon[] { iconRegister.func_94245_a("buildcraftcore:triggers/trigger_dir_down"), iconRegister.func_94245_a("buildcraftcore:triggers/trigger_dir_up"), iconRegister.func_94245_a("buildcraftcore:triggers/trigger_dir_north"), iconRegister.func_94245_a("buildcraftcore:triggers/trigger_dir_south"), iconRegister.func_94245_a("buildcraftcore:triggers/trigger_dir_west"), iconRegister.func_94245_a("buildcraftcore:triggers/trigger_dir_east") };
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public IStatementParameter rotateLeft() {
/* 118 */     StatementParameterDirection d = new StatementParameterDirection();
/* 119 */     d.direction = this.direction.getRotation(ForgeDirection.UP);
/* 120 */     return d;
/*     */   }
/*     */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-core.jar!\buildcraft\core\statements\StatementParameterDirection.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */