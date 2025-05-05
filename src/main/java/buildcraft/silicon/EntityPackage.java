/*    */ package buildcraft.silicon;
/*    */ 
/*    */ import buildcraft.BuildCraftSilicon;
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.entity.EntityLivingBase;
/*    */ import net.minecraft.entity.item.EntityItem;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraft.entity.projectile.EntityThrowable;
/*    */ import net.minecraft.item.Item;
/*    */ import net.minecraft.item.ItemStack;
/*    */ import net.minecraft.nbt.NBTBase;
/*    */ import net.minecraft.nbt.NBTTagCompound;
/*    */ import net.minecraft.util.DamageSource;
/*    */ import net.minecraft.util.MovingObjectPosition;
/*    */ import net.minecraft.world.World;
/*    */ 
/*    */ public class EntityPackage extends EntityThrowable {
/*    */   public EntityPackage(World world) {
/* 19 */     super(world);
/* 20 */     this.pkg = new ItemStack((Item)BuildCraftSilicon.packageItem);
/*    */   }
/*    */   private ItemStack pkg;
/*    */   public EntityPackage(World world, EntityPlayer player, ItemStack stack) {
/* 24 */     super(world, (EntityLivingBase)player);
/* 25 */     this.pkg = stack;
/*    */   }
/*    */   
/*    */   public EntityPackage(World world, double x, double y, double z, ItemStack stack) {
/* 29 */     super(world, x, y, z);
/* 30 */     this.pkg = stack;
/*    */   }
/*    */ 
/*    */   
/*    */   public void func_70109_d(NBTTagCompound compound) {
/* 35 */     super.func_70109_d(compound);
/* 36 */     NBTTagCompound subTag = new NBTTagCompound();
/* 37 */     this.pkg.func_77955_b(subTag);
/* 38 */     compound.func_74782_a("stack", (NBTBase)subTag);
/*    */   }
/*    */ 
/*    */   
/*    */   public void func_70020_e(NBTTagCompound compound) {
/* 43 */     super.func_70020_e(compound);
/* 44 */     if (compound.func_74764_b("stack")) {
/* 45 */       this.pkg = ItemStack.func_77949_a(compound.func_74775_l("stack"));
/*    */     }
/*    */   }
/*    */ 
/*    */   
/*    */   protected void func_70184_a(MovingObjectPosition target) {
/*    */     double x, y, z;
/* 52 */     if (target.field_72313_a == MovingObjectPosition.MovingObjectType.ENTITY) {
/* 53 */       x = target.field_72308_g.field_70165_t;
/* 54 */       y = target.field_72308_g.field_70163_u;
/* 55 */       z = target.field_72308_g.field_70161_v;
/*    */     } else {
/* 57 */       x = target.field_72311_b;
/* 58 */       y = target.field_72312_c;
/* 59 */       z = target.field_72309_d;
/*    */     } 
/*    */     
/* 62 */     float hitPoints = 0.0F;
/* 63 */     for (int i = 0; i < 9; i++) {
/* 64 */       ItemStack stack = ItemPackage.getStack(this.pkg, i);
/* 65 */       if (stack != null) {
/* 66 */         if (stack.func_77973_b() instanceof net.minecraft.item.ItemBlock) {
/* 67 */           hitPoints += 0.28F;
/*    */         } else {
/* 69 */           hitPoints += 0.14F;
/*    */         } 
/* 71 */         float var = 0.7F;
/* 72 */         World world = this.field_70170_p;
/* 73 */         double dx = (world.field_73012_v.nextFloat() * var) + (1.0F - var) * 0.5D;
/* 74 */         double dy = (world.field_73012_v.nextFloat() * var) + (1.0F - var) * 0.5D;
/* 75 */         double dz = (world.field_73012_v.nextFloat() * var) + (1.0F - var) * 0.5D;
/* 76 */         EntityItem entityitem = new EntityItem(world, x + dx, y + dy, z + dz, stack);
/* 77 */         entityitem.field_145804_b = 10;
/*    */         
/* 79 */         world.func_72838_d((Entity)entityitem);
/*    */       } 
/*    */     } 
/*    */     
/* 83 */     if (target.field_72313_a == MovingObjectPosition.MovingObjectType.ENTITY) {
/* 84 */       target.field_72308_g.func_70097_a(DamageSource.func_76356_a((Entity)this, (Entity)this), hitPoints);
/*    */     }
/*    */     
/* 87 */     func_70106_y();
/*    */   }
/*    */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-silicon.jar!\buildcraft\silicon\EntityPackage.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */