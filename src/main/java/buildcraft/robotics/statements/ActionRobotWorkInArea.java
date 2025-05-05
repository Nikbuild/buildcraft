/*     */ package buildcraft.robotics.statements;
/*     */ 
/*     */ import buildcraft.api.core.IZone;
/*     */ import buildcraft.api.items.IMapLocation;
/*     */ import buildcraft.api.statements.IActionInternal;
/*     */ import buildcraft.api.statements.IStatementContainer;
/*     */ import buildcraft.api.statements.IStatementParameter;
/*     */ import buildcraft.api.statements.StatementSlot;
/*     */ import buildcraft.core.lib.utils.StringUtils;
/*     */ import buildcraft.core.statements.BCStatement;
/*     */ import net.minecraft.client.renderer.texture.IIconRegister;
/*     */ import net.minecraft.item.ItemStack;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ActionRobotWorkInArea
/*     */   extends BCStatement
/*     */   implements IActionInternal
/*     */ {
/*     */   private final AreaType areaType;
/*     */   
/*     */   public enum AreaType
/*     */   {
/*  26 */     WORK("work_in_area"),
/*  27 */     LOAD_UNLOAD("load_unload_area");
/*     */     
/*     */     private final String name;
/*     */     
/*     */     AreaType(String iName) {
/*  32 */       this.name = iName;
/*     */     }
/*     */     
/*     */     public String getTag() {
/*  36 */       return "buildcraft:robot." + this.name;
/*     */     }
/*     */     
/*     */     public String getUnlocalizedName() {
/*  40 */       return "gate.action.robot." + this.name;
/*     */     }
/*     */     
/*     */     public String getIcon() {
/*  44 */       return "buildcraftrobotics:triggers/action_robot_" + this.name;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public ActionRobotWorkInArea(AreaType iAreaType) {
/*  51 */     super(new String[] { iAreaType.getTag() });
/*     */     
/*  53 */     this.areaType = iAreaType;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getDescription() {
/*  58 */     return StringUtils.localize(this.areaType.getUnlocalizedName());
/*     */   }
/*     */ 
/*     */   
/*     */   public void registerIcons(IIconRegister iconRegister) {
/*  63 */     this.icon = iconRegister.func_94245_a(this.areaType.getIcon());
/*     */   }
/*     */   
/*     */   public static IZone getArea(StatementSlot slot) {
/*  67 */     if (slot.parameters[0] == null) {
/*  68 */       return null;
/*     */     }
/*     */     
/*  71 */     ItemStack stack = slot.parameters[0].getItemStack();
/*     */     
/*  73 */     if (stack == null || !(stack.func_77973_b() instanceof IMapLocation)) {
/*  74 */       return null;
/*     */     }
/*     */     
/*  77 */     IMapLocation map = (IMapLocation)stack.func_77973_b();
/*  78 */     return map.getZone(stack);
/*     */   }
/*     */ 
/*     */   
/*     */   public int minParameters() {
/*  83 */     return 1;
/*     */   }
/*     */ 
/*     */   
/*     */   public int maxParameters() {
/*  88 */     return 1;
/*     */   }
/*     */ 
/*     */   
/*     */   public IStatementParameter createParameter(int index) {
/*  93 */     return (IStatementParameter)new StatementParameterMapLocation();
/*     */   }
/*     */ 
/*     */   
/*     */   public void actionActivate(IStatementContainer source, IStatementParameter[] parameters) {}
/*     */ 
/*     */   
/*     */   public AreaType getAreaType() {
/* 101 */     return this.areaType;
/*     */   }
/*     */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-robotics.jar!\buildcraft\robotics\statements\ActionRobotWorkInArea.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */