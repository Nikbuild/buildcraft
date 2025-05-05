/*    */ package buildcraft.api.properties;
/*    */ 
/*    */ import buildcraft.api.enums.EnumDecoratedBlock;
/*    */ import buildcraft.api.enums.EnumEngineType;
/*    */ import buildcraft.api.enums.EnumFillerPattern;
/*    */ import buildcraft.api.enums.EnumLaserTableType;
/*    */ import buildcraft.api.enums.EnumMachineState;
/*    */ import buildcraft.api.enums.EnumOptionalSnapshotType;
/*    */ import buildcraft.api.enums.EnumPowerStage;
/*    */ import buildcraft.api.enums.EnumSpring;
/*    */ import com.google.common.collect.Maps;
/*    */ import java.util.Map;
/*    */ import net.minecraft.block.properties.IProperty;
/*    */ import net.minecraft.block.properties.PropertyBool;
/*    */ import net.minecraft.block.properties.PropertyEnum;
/*    */ import net.minecraft.block.properties.PropertyInteger;
/*    */ import net.minecraft.item.EnumDyeColor;
/*    */ import net.minecraft.util.EnumFacing;
/*    */ 
/*    */ 
/*    */ 
/*    */ public final class BuildCraftProperties
/*    */ {
/* 24 */   public static final IProperty<EnumFacing> BLOCK_FACING = (IProperty<EnumFacing>)PropertyEnum.func_177706_a("facing", EnumFacing.class, (Enum[])EnumFacing.Plane.HORIZONTAL.func_179516_a());
/* 25 */   public static final IProperty<EnumFacing> BLOCK_FACING_6 = (IProperty<EnumFacing>)PropertyEnum.func_177709_a("facing", EnumFacing.class);
/*    */   
/* 27 */   public static final IProperty<EnumDyeColor> BLOCK_COLOR = (IProperty<EnumDyeColor>)PropertyEnum.func_177709_a("color", EnumDyeColor.class);
/* 28 */   public static final IProperty<EnumSpring> SPRING_TYPE = (IProperty<EnumSpring>)PropertyEnum.func_177709_a("type", EnumSpring.class);
/* 29 */   public static final IProperty<EnumEngineType> ENGINE_TYPE = (IProperty<EnumEngineType>)PropertyEnum.func_177709_a("type", EnumEngineType.class);
/* 30 */   public static final IProperty<EnumLaserTableType> LASER_TABLE_TYPE = (IProperty<EnumLaserTableType>)PropertyEnum.func_177709_a("type", EnumLaserTableType.class);
/* 31 */   public static final IProperty<EnumMachineState> MACHINE_STATE = (IProperty<EnumMachineState>)PropertyEnum.func_177709_a("state", EnumMachineState.class);
/* 32 */   public static final IProperty<EnumPowerStage> ENERGY_STAGE = (IProperty<EnumPowerStage>)PropertyEnum.func_177709_a("stage", EnumPowerStage.class);
/* 33 */   public static final IProperty<EnumOptionalSnapshotType> SNAPSHOT_TYPE = (IProperty<EnumOptionalSnapshotType>)PropertyEnum.func_177709_a("snapshot_type", EnumOptionalSnapshotType.class);
/* 34 */   public static final IProperty<EnumDecoratedBlock> DECORATED_BLOCK = (IProperty<EnumDecoratedBlock>)PropertyEnum.func_177709_a("decoration_type", EnumDecoratedBlock.class);
/*    */   
/* 36 */   public static final IProperty<Integer> GENERIC_PIPE_DATA = (IProperty<Integer>)PropertyInteger.func_177719_a("pipe_data", 0, 15);
/* 37 */   public static final IProperty<Integer> LED_POWER = (IProperty<Integer>)PropertyInteger.func_177719_a("led_power", 0, 3);
/*    */   
/* 39 */   public static final IProperty<Boolean> JOINED_BELOW = (IProperty<Boolean>)PropertyBool.func_177716_a("joined_below");
/* 40 */   public static final IProperty<Boolean> MOVING = (IProperty<Boolean>)PropertyBool.func_177716_a("moving");
/* 41 */   public static final IProperty<Boolean> LED_DONE = (IProperty<Boolean>)PropertyBool.func_177716_a("led_done");
/* 42 */   public static final IProperty<Boolean> ACTIVE = (IProperty<Boolean>)PropertyBool.func_177716_a("active");
/* 43 */   public static final IProperty<Boolean> VALID = (IProperty<Boolean>)PropertyBool.func_177716_a("valid");
/*    */   
/* 45 */   public static final IProperty<Boolean> CONNECTED_UP = (IProperty<Boolean>)PropertyBool.func_177716_a("connected_up");
/* 46 */   public static final IProperty<Boolean> CONNECTED_DOWN = (IProperty<Boolean>)PropertyBool.func_177716_a("connected_down");
/* 47 */   public static final IProperty<Boolean> CONNECTED_EAST = (IProperty<Boolean>)PropertyBool.func_177716_a("connected_east");
/* 48 */   public static final IProperty<Boolean> CONNECTED_WEST = (IProperty<Boolean>)PropertyBool.func_177716_a("connected_west");
/* 49 */   public static final IProperty<Boolean> CONNECTED_NORTH = (IProperty<Boolean>)PropertyBool.func_177716_a("connected_north");
/* 50 */   public static final IProperty<Boolean> CONNECTED_SOUTH = (IProperty<Boolean>)PropertyBool.func_177716_a("connected_south");
/*    */   
/*    */   public static final Map<EnumFacing, IProperty<Boolean>> CONNECTED_MAP;
/*    */   
/* 54 */   public static final IProperty<EnumFillerPattern> FILLER_PATTERN = (IProperty<EnumFillerPattern>)PropertyEnum.func_177709_a("pattern", EnumFillerPattern.class);
/*    */ 
/*    */   
/*    */   public static final int UPDATE_NONE = 0;
/*    */ 
/*    */   
/*    */   public static final int UPDATE_NEIGHBOURS = 1;
/*    */ 
/*    */   
/*    */   public static final int MARK_BLOCK_FOR_UPDATE = 2;
/*    */ 
/*    */   
/*    */   public static final int UPDATE_EVEN_CLIENT = 6;
/*    */ 
/*    */   
/*    */   public static final int MARK_THIS_AND_NEIGHBOURS = 3;
/*    */ 
/*    */   
/*    */   public static final int UPDATE_ALL = 9;
/*    */ 
/*    */   
/*    */   static {
/* 76 */     Map<EnumFacing, IProperty<Boolean>> map = Maps.newEnumMap(EnumFacing.class);
/* 77 */     map.put(EnumFacing.DOWN, CONNECTED_DOWN);
/* 78 */     map.put(EnumFacing.UP, CONNECTED_UP);
/* 79 */     map.put(EnumFacing.EAST, CONNECTED_EAST);
/* 80 */     map.put(EnumFacing.WEST, CONNECTED_WEST);
/* 81 */     map.put(EnumFacing.NORTH, CONNECTED_NORTH);
/* 82 */     map.put(EnumFacing.SOUTH, CONNECTED_SOUTH);
/* 83 */     CONNECTED_MAP = (Map<EnumFacing, IProperty<Boolean>>)Maps.immutableEnumMap(map);
/*    */   }
/*    */ }


/* Location:              C:\Users\Nikit\files\buildcraft-api-7.99.8.2.jar!\buildcraft\api\properties\BuildCraftProperties.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */