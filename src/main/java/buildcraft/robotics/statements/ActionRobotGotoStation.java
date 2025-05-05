/*     */ package buildcraft.robotics.statements;
/*     */ 
/*     */ import buildcraft.api.core.BlockIndex;
/*     */ import buildcraft.api.items.IMapLocation;
/*     */ import buildcraft.api.robots.AIRobot;
/*     */ import buildcraft.api.robots.DockingStation;
/*     */ import buildcraft.api.robots.EntityRobotBase;
/*     */ import buildcraft.api.robots.IRobotRegistry;
/*     */ import buildcraft.api.robots.RobotManager;
/*     */ import buildcraft.api.statements.IActionInternal;
/*     */ import buildcraft.api.statements.IStatementContainer;
/*     */ import buildcraft.api.statements.IStatementParameter;
/*     */ import buildcraft.api.statements.StatementParameterItemStack;
/*     */ import buildcraft.core.lib.utils.StringUtils;
/*     */ import buildcraft.core.statements.BCStatement;
/*     */ import buildcraft.robotics.EntityRobot;
/*     */ import buildcraft.robotics.RobotUtils;
/*     */ import buildcraft.robotics.ai.AIRobotGoAndLinkToDock;
/*     */ import java.util.List;
/*     */ import net.minecraft.client.renderer.texture.IIconRegister;
/*     */ import net.minecraft.item.ItemStack;
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
/*     */ public class ActionRobotGotoStation
/*     */   extends BCStatement
/*     */   implements IActionInternal
/*     */ {
/*     */   public ActionRobotGotoStation() {
/*  37 */     super(new String[] { "buildcraft:robot.goto_station" });
/*     */   }
/*     */ 
/*     */   
/*     */   public String getDescription() {
/*  42 */     return StringUtils.localize("gate.action.robot.goto_station");
/*     */   }
/*     */ 
/*     */   
/*     */   public void registerIcons(IIconRegister iconRegister) {
/*  47 */     this.icon = iconRegister.func_94245_a("buildcraftrobotics:triggers/action_robot_goto_station");
/*     */   }
/*     */ 
/*     */   
/*     */   public void actionActivate(IStatementContainer container, IStatementParameter[] parameters) {
/*  52 */     IRobotRegistry registry = RobotManager.registryProvider.getRegistry(container.getTile()
/*  53 */         .func_145831_w());
/*     */     
/*  55 */     List<DockingStation> stations = RobotUtils.getStations(container.getTile());
/*     */     
/*  57 */     for (DockingStation station : stations) {
/*  58 */       if (station.robotTaking() != null) {
/*  59 */         EntityRobot robot = (EntityRobot)station.robotTaking();
/*  60 */         AIRobot ai = robot.getOverridingAI();
/*     */         
/*  62 */         if (ai != null) {
/*     */           continue;
/*     */         }
/*     */         
/*  66 */         DockingStation newStation = station;
/*     */         
/*  68 */         if (parameters[0] != null) {
/*  69 */           newStation = getStation((StatementParameterItemStack)parameters[0], registry);
/*     */         }
/*     */         
/*  72 */         if (newStation != null) {
/*  73 */           robot.overrideAI((AIRobot)new AIRobotGoAndLinkToDock((EntityRobotBase)robot, newStation));
/*     */         }
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private DockingStation getStation(StatementParameterItemStack stackParam, IRobotRegistry registry) {
/*  81 */     ItemStack item = stackParam.getItemStack();
/*     */     
/*  83 */     if (item != null && item.func_77973_b() instanceof IMapLocation) {
/*  84 */       IMapLocation map = (IMapLocation)item.func_77973_b();
/*  85 */       BlockIndex index = map.getPoint(item);
/*     */       
/*  87 */       if (index != null) {
/*  88 */         ForgeDirection side = map.getPointSide(item);
/*  89 */         DockingStation paramStation = registry.getStation(index.x, index.y, index.z, side);
/*     */         
/*  91 */         if (paramStation != null) {
/*  92 */           return paramStation;
/*     */         }
/*     */       } 
/*     */     } 
/*  96 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public int maxParameters() {
/* 101 */     return 1;
/*     */   }
/*     */ 
/*     */   
/*     */   public IStatementParameter createParameter(int index) {
/* 106 */     return (IStatementParameter)new StatementParameterItemStack();
/*     */   }
/*     */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-robotics.jar!\buildcraft\robotics\statements\ActionRobotGotoStation.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */