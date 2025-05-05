/*     */ package buildcraft.core.lib.utils;
/*     */ 
/*     */ import buildcraft.api.core.BlockIndex;
/*     */ import buildcraft.api.core.BuildCraftAPI;
/*     */ import buildcraft.api.core.IZone;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.HashSet;
/*     */ import java.util.Iterator;
/*     */ import java.util.LinkedList;
/*     */ import java.util.List;
/*     */ import net.minecraft.world.World;
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
/*     */ public class PathFindingSearch
/*     */   implements IIterableAlgorithm
/*     */ {
/*     */   public static final int PATH_ITERATIONS = 1000;
/*  27 */   private static final HashMap<Integer, HashSet<BlockIndex>> reservations = new HashMap<Integer, HashSet<BlockIndex>>();
/*     */   
/*     */   private World world;
/*     */   
/*     */   private BlockIndex start;
/*     */   
/*     */   private List<PathFinding> pathFinders;
/*     */   
/*     */   private IBlockFilter pathFound;
/*     */   private IZone zone;
/*     */   private float maxDistance;
/*     */   private Iterator<BlockIndex> blockIter;
/*     */   private double maxDistanceToEnd;
/*     */   
/*     */   public PathFindingSearch(World iWorld, BlockIndex iStart, Iterator<BlockIndex> iBlockIter, IBlockFilter iPathFound, double iMaxDistanceToEnd, float iMaxDistance, IZone iZone) {
/*  42 */     this.world = iWorld;
/*  43 */     this.start = iStart;
/*  44 */     this.pathFound = iPathFound;
/*     */     
/*  46 */     this.maxDistance = iMaxDistance;
/*  47 */     this.maxDistanceToEnd = iMaxDistanceToEnd;
/*  48 */     this.zone = iZone;
/*  49 */     this.blockIter = iBlockIter;
/*     */     
/*  51 */     this.pathFinders = new LinkedList<PathFinding>();
/*     */   }
/*     */ 
/*     */   
/*     */   public void iterate() {
/*  56 */     if (this.pathFinders.size() < 5 && this.blockIter.hasNext()) {
/*  57 */       iterateSearch(10000);
/*     */     }
/*  59 */     iteratePathFind(1000);
/*     */   }
/*     */   
/*     */   private void iterateSearch(int itNumber) {
/*  63 */     for (int i = 0; i < itNumber; i++) {
/*  64 */       if (!this.blockIter.hasNext()) {
/*     */         return;
/*     */       }
/*     */       
/*  68 */       BlockIndex delta = this.blockIter.next();
/*  69 */       BlockIndex block = new BlockIndex(this.start.x + delta.x, (this.start.y + delta.y > 0) ? (this.start.y + delta.y) : 0, this.start.z + delta.z);
/*     */ 
/*     */       
/*  72 */       if (isLoadedChunk(block.x, block.z) && 
/*  73 */         isTarget(block)) {
/*  74 */         this.pathFinders.add(new PathFinding(this.world, this.start, block, this.maxDistanceToEnd, this.maxDistance));
/*     */       }
/*     */ 
/*     */       
/*  78 */       if (this.pathFinders.size() >= 5) {
/*     */         return;
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   private boolean isTarget(BlockIndex block) {
/*  85 */     if (this.zone != null && !this.zone.contains(block.x, block.y, block.z)) {
/*  86 */       return false;
/*     */     }
/*  88 */     if (!this.pathFound.matches(this.world, block.x, block.y, block.z)) {
/*  89 */       return false;
/*     */     }
/*  91 */     synchronized (reservations) {
/*  92 */       if (reservations.containsKey(Integer.valueOf(this.world.field_73011_w.field_76574_g))) {
/*     */         
/*  94 */         HashSet<BlockIndex> dimReservations = reservations.get(Integer.valueOf(this.world.field_73011_w.field_76574_g));
/*  95 */         if (dimReservations.contains(block)) {
/*  96 */           return false;
/*     */         }
/*     */       } 
/*     */     } 
/* 100 */     if (!BuildCraftAPI.isSoftBlock(this.world, block.x - 1, block.y, block.z) && 
/* 101 */       !BuildCraftAPI.isSoftBlock(this.world, block.x + 1, block.y, block.z) && 
/* 102 */       !BuildCraftAPI.isSoftBlock(this.world, block.x, block.y, block.z - 1) && 
/* 103 */       !BuildCraftAPI.isSoftBlock(this.world, block.x, block.y, block.z + 1) && 
/* 104 */       !BuildCraftAPI.isSoftBlock(this.world, block.x, block.y - 1, block.z) && 
/* 105 */       !BuildCraftAPI.isSoftBlock(this.world, block.x, block.y + 1, block.z)) {
/* 106 */       return false;
/*     */     }
/* 108 */     return true;
/*     */   }
/*     */   
/*     */   private boolean isLoadedChunk(int x, int z) {
/* 112 */     return this.world.func_72863_F().func_73149_a(x >> 4, z >> 4);
/*     */   }
/*     */   
/*     */   public void iteratePathFind(int itNumber) {
/* 116 */     for (PathFinding pathFinding : new ArrayList(this.pathFinders)) {
/* 117 */       pathFinding.iterate(itNumber / this.pathFinders.size());
/* 118 */       if (pathFinding.isDone()) {
/* 119 */         LinkedList<BlockIndex> path = pathFinding.getResult();
/* 120 */         if (path != null && path.size() > 0 && 
/* 121 */           reserve(pathFinding.end())) {
/*     */           return;
/*     */         }
/*     */         
/* 125 */         this.pathFinders.remove(pathFinding);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isDone() {
/* 132 */     for (PathFinding pathFinding : this.pathFinders) {
/* 133 */       if (pathFinding.isDone()) {
/* 134 */         return true;
/*     */       }
/*     */     } 
/* 137 */     return !this.blockIter.hasNext();
/*     */   }
/*     */   
/*     */   public LinkedList<BlockIndex> getResult() {
/* 141 */     for (PathFinding pathFinding : this.pathFinders) {
/* 142 */       if (pathFinding.isDone()) {
/* 143 */         return pathFinding.getResult();
/*     */       }
/*     */     } 
/* 146 */     return new LinkedList<BlockIndex>();
/*     */   }
/*     */   
/*     */   public BlockIndex getResultTarget() {
/* 150 */     for (PathFinding pathFinding : this.pathFinders) {
/* 151 */       if (pathFinding.isDone()) {
/* 152 */         return pathFinding.end();
/*     */       }
/*     */     } 
/* 155 */     return null;
/*     */   }
/*     */   
/*     */   private boolean reserve(BlockIndex block) {
/* 159 */     synchronized (reservations) {
/* 160 */       if (!reservations.containsKey(Integer.valueOf(this.world.field_73011_w.field_76574_g))) {
/* 161 */         reservations.put(Integer.valueOf(this.world.field_73011_w.field_76574_g), new HashSet<BlockIndex>());
/*     */       }
/*     */ 
/*     */       
/* 165 */       HashSet<BlockIndex> dimReservations = reservations.get(Integer.valueOf(this.world.field_73011_w.field_76574_g));
/* 166 */       if (dimReservations.contains(block)) {
/* 167 */         return false;
/*     */       }
/* 169 */       dimReservations.add(block);
/* 170 */       return true;
/*     */     } 
/*     */   }
/*     */   
/*     */   public void unreserve(BlockIndex block) {
/* 175 */     synchronized (reservations) {
/* 176 */       if (reservations.containsKey(Integer.valueOf(this.world.field_73011_w.field_76574_g)))
/* 177 */         ((HashSet)reservations.get(Integer.valueOf(this.world.field_73011_w.field_76574_g))).remove(block); 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-core.jar!\buildcraft\core\li\\utils\PathFindingSearch.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */