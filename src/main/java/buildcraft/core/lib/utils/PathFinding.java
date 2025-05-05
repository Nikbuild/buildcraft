/*     */ package buildcraft.core.lib.utils;
/*     */ 
/*     */ import buildcraft.api.core.BlockIndex;
/*     */ import buildcraft.api.core.BuildCraftAPI;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
/*     */ import java.util.HashMap;
/*     */ import java.util.LinkedList;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class PathFinding
/*     */   implements IIterableAlgorithm
/*     */ {
/*  28 */   public static int PATH_ITERATIONS = 1000;
/*     */   
/*     */   private World world;
/*     */   private BlockIndex start;
/*     */   private BlockIndex end;
/*  33 */   private double maxDistanceToEndSq = 0.0D;
/*  34 */   private float maxTotalDistanceSq = 0.0F;
/*     */   
/*  36 */   private HashMap<BlockIndex, Node> openList = new HashMap<BlockIndex, Node>();
/*  37 */   private HashMap<BlockIndex, Node> closedList = new HashMap<BlockIndex, Node>();
/*     */   
/*     */   private Node nextIteration;
/*     */   
/*     */   private LinkedList<BlockIndex> result;
/*     */   
/*     */   private boolean endReached = false;
/*     */   
/*     */   public PathFinding(World iWorld, BlockIndex iStart, BlockIndex iEnd) {
/*  46 */     this.world = iWorld;
/*  47 */     this.start = iStart;
/*  48 */     this.end = iEnd;
/*     */     
/*  50 */     Node startNode = new Node();
/*  51 */     startNode.parent = null;
/*  52 */     startNode.movementCost = 0.0D;
/*  53 */     startNode.destinationCost = distanceSq(this.start, this.end);
/*  54 */     startNode.totalWeight = startNode.movementCost + startNode.destinationCost;
/*  55 */     startNode.index = iStart;
/*  56 */     this.openList.put(this.start, startNode);
/*  57 */     this.nextIteration = startNode;
/*     */   }
/*     */   
/*     */   public PathFinding(World iWorld, BlockIndex iStart, BlockIndex iEnd, double iMaxDistanceToEnd) {
/*  61 */     this(iWorld, iStart, iEnd);
/*     */     
/*  63 */     this.maxDistanceToEndSq = iMaxDistanceToEnd * iMaxDistanceToEnd;
/*     */   }
/*     */ 
/*     */   
/*     */   public PathFinding(World iWorld, BlockIndex iStart, BlockIndex iEnd, double iMaxDistanceToEnd, float iMaxTotalDistance) {
/*  68 */     this(iWorld, iStart, iEnd, iMaxDistanceToEnd);
/*     */     
/*  70 */     this.maxTotalDistanceSq = iMaxTotalDistance * iMaxTotalDistance;
/*     */   }
/*     */ 
/*     */   
/*     */   public void iterate() {
/*  75 */     iterate(PATH_ITERATIONS);
/*     */   }
/*     */   
/*     */   public void iterate(int itNumber) {
/*  79 */     for (int i = 0; i < itNumber; i++) {
/*  80 */       if (this.nextIteration == null) {
/*     */         return;
/*     */       }
/*     */       
/*  84 */       if (this.endReached) {
/*  85 */         this.result = new LinkedList<BlockIndex>();
/*     */         
/*  87 */         while (this.nextIteration != null) {
/*  88 */           this.result.addFirst(this.nextIteration.index);
/*  89 */           this.nextIteration = this.nextIteration.parent;
/*     */         } 
/*     */         
/*     */         return;
/*     */       } 
/*  94 */       this.nextIteration = iterate(this.nextIteration);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isDone() {
/* 101 */     return (this.nextIteration == null);
/*     */   }
/*     */   
/*     */   public LinkedList<BlockIndex> getResult() {
/* 105 */     if (this.result != null) {
/* 106 */       return this.result;
/*     */     }
/* 108 */     return new LinkedList<BlockIndex>();
/*     */   }
/*     */ 
/*     */   
/*     */   public BlockIndex end() {
/* 113 */     return this.end;
/*     */   }
/*     */   
/*     */   private Node iterate(Node from) {
/* 117 */     this.openList.remove(from.index);
/* 118 */     this.closedList.put(from.index, from);
/*     */     
/* 120 */     ArrayList<Node> nodes = new ArrayList<Node>();
/* 121 */     byte[][][] resultMoves = movements(from);
/*     */     
/* 123 */     for (int dx = -1; dx <= 1; dx++) {
/* 124 */       for (int dy = -1; dy <= 1; dy++) {
/* 125 */         for (int dz = -1; dz <= 1; dz++) {
/* 126 */           if (resultMoves[dx + 1][dy + 1][dz + 1] != 0) {
/*     */ 
/*     */ 
/*     */             
/* 130 */             int x = from.index.x + dx;
/* 131 */             int y = from.index.y + dy;
/* 132 */             int z = from.index.z + dz;
/*     */             
/* 134 */             Node nextNode = new Node();
/* 135 */             nextNode.parent = from;
/* 136 */             nextNode.index = new BlockIndex(x, y, z);
/*     */             
/* 138 */             if (resultMoves[dx + 1][dy + 1][dz + 1] == 2) {
/* 139 */               this.endReached = true;
/* 140 */               return nextNode;
/*     */             } 
/*     */             
/* 143 */             from.movementCost += distanceSq(nextNode.index, from.index);
/* 144 */             nextNode.destinationCost = distanceSq(nextNode.index, this.end);
/* 145 */             nextNode.totalWeight = nextNode.movementCost + nextNode.destinationCost;
/*     */             
/* 147 */             if (this.maxTotalDistanceSq > 0.0F && nextNode.totalWeight > this.maxTotalDistanceSq) {
/* 148 */               if (!this.closedList.containsKey(nextNode.index)) {
/* 149 */                 this.closedList.put(nextNode.index, nextNode);
/*     */               
/*     */               }
/*     */             }
/* 153 */             else if (!this.closedList.containsKey(nextNode.index)) {
/*     */               
/* 155 */               if (this.openList.containsKey(nextNode.index)) {
/* 156 */                 Node tentative = this.openList.get(nextNode.index);
/*     */                 
/* 158 */                 if (tentative.movementCost < nextNode.movementCost) {
/* 159 */                   nextNode = tentative;
/*     */                 } else {
/* 161 */                   this.openList.put(nextNode.index, nextNode);
/*     */                 } 
/*     */               } else {
/* 164 */                 this.openList.put(nextNode.index, nextNode);
/*     */               } 
/*     */               
/* 167 */               nodes.add(nextNode);
/*     */             } 
/*     */           } 
/*     */         } 
/*     */       } 
/* 172 */     }  nodes.addAll(this.openList.values());
/*     */     
/* 174 */     return findSmallerWeight(nodes);
/*     */   }
/*     */   
/*     */   private Node findSmallerWeight(Collection<Node> collection) {
/* 178 */     Node found = null;
/*     */     
/* 180 */     for (Node n : collection) {
/* 181 */       if (found == null) {
/* 182 */         found = n; continue;
/* 183 */       }  if (n.totalWeight < found.totalWeight) {
/* 184 */         found = n;
/*     */       }
/*     */     } 
/*     */     
/* 188 */     return found;
/*     */   }
/*     */   private static class Node {
/*     */     public Node parent;
/*     */     public double movementCost;
/*     */     public double destinationCost;
/*     */     public double totalWeight;
/*     */     public BlockIndex index;
/*     */     
/*     */     private Node() {} }
/*     */   
/*     */   private static double distanceSq(BlockIndex i1, BlockIndex i2) {
/* 200 */     double dx = i1.x - i2.x;
/* 201 */     double dy = i1.y - i2.y;
/* 202 */     double dz = i1.z - i2.z;
/*     */     
/* 204 */     return dx * dx + dy * dy + dz * dz;
/*     */   }
/*     */   
/*     */   private boolean endReached(int x, int y, int z) {
/* 208 */     if (this.maxDistanceToEndSq == 0.0D) {
/* 209 */       return (this.end.x == x && this.end.y == y && this.end.z == z);
/*     */     }
/* 211 */     return (BuildCraftAPI.isSoftBlock(this.world, x, y, z) && 
/* 212 */       distanceSq(new BlockIndex(x, y, z), this.end) <= this.maxDistanceToEndSq);
/*     */   }
/*     */ 
/*     */   
/*     */   private byte[][][] movements(Node from) {
/* 217 */     byte[][][] resultMoves = new byte[3][3][3];
/*     */     
/* 219 */     for (int dx = -1; dx <= 1; dx++) {
/* 220 */       for (int dy = -1; dy <= 1; dy++) {
/* 221 */         for (int dz = -1; dz <= 1; dz++) {
/* 222 */           int x = from.index.x + dx;
/* 223 */           int y = from.index.y + dy;
/* 224 */           int z = from.index.z + dz;
/*     */           
/* 226 */           if (y < 0) {
/* 227 */             resultMoves[dx + 1][dy + 1][dz + 1] = 0;
/* 228 */           } else if (endReached(x, y, z)) {
/* 229 */             resultMoves[dx + 1][dy + 1][dz + 1] = 2;
/* 230 */           } else if (!BuildCraftAPI.isSoftBlock(this.world, x, y, z)) {
/* 231 */             resultMoves[dx + 1][dy + 1][dz + 1] = 0;
/*     */           } else {
/* 233 */             resultMoves[dx + 1][dy + 1][dz + 1] = 1;
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 239 */     resultMoves[1][1][1] = 0;
/*     */     
/* 241 */     if (resultMoves[0][1][1] == 0) {
/* 242 */       for (int i = 0; i <= 2; i++) {
/* 243 */         for (int j = 0; j <= 2; j++) {
/* 244 */           resultMoves[0][i][j] = 0;
/*     */         }
/*     */       } 
/*     */     }
/*     */     
/* 249 */     if (resultMoves[2][1][1] == 0) {
/* 250 */       for (int i = 0; i <= 2; i++) {
/* 251 */         for (int j = 0; j <= 2; j++) {
/* 252 */           resultMoves[2][i][j] = 0;
/*     */         }
/*     */       } 
/*     */     }
/*     */     
/* 257 */     if (resultMoves[1][0][1] == 0) {
/* 258 */       for (int i = 0; i <= 2; i++) {
/* 259 */         for (int j = 0; j <= 2; j++) {
/* 260 */           resultMoves[i][0][j] = 0;
/*     */         }
/*     */       } 
/*     */     }
/*     */     
/* 265 */     if (resultMoves[1][2][1] == 0) {
/* 266 */       for (int i = 0; i <= 2; i++) {
/* 267 */         for (int j = 0; j <= 2; j++) {
/* 268 */           resultMoves[i][2][j] = 0;
/*     */         }
/*     */       } 
/*     */     }
/*     */     
/* 273 */     if (resultMoves[1][1][0] == 0) {
/* 274 */       for (int i = 0; i <= 2; i++) {
/* 275 */         for (int j = 0; j <= 2; j++) {
/* 276 */           resultMoves[i][j][0] = 0;
/*     */         }
/*     */       } 
/*     */     }
/*     */     
/* 281 */     if (resultMoves[1][1][2] == 0) {
/* 282 */       for (int i = 0; i <= 2; i++) {
/* 283 */         for (int j = 0; j <= 2; j++) {
/* 284 */           resultMoves[i][j][2] = 0;
/*     */         }
/*     */       } 
/*     */     }
/*     */     
/* 289 */     if (resultMoves[0][0][1] == 0) {
/* 290 */       resultMoves[0][0][0] = 0;
/* 291 */       resultMoves[0][0][2] = 0;
/*     */     } 
/*     */     
/* 294 */     if (resultMoves[0][2][1] == 0) {
/* 295 */       resultMoves[0][2][0] = 0;
/* 296 */       resultMoves[0][2][2] = 0;
/*     */     } 
/*     */     
/* 299 */     if (resultMoves[2][0][1] == 0) {
/* 300 */       resultMoves[2][0][0] = 0;
/* 301 */       resultMoves[2][0][2] = 0;
/*     */     } 
/*     */     
/* 304 */     if (resultMoves[2][2][1] == 0) {
/* 305 */       resultMoves[2][2][0] = 0;
/* 306 */       resultMoves[2][2][2] = 0;
/*     */     } 
/*     */     
/* 309 */     if (resultMoves[0][1][0] == 0) {
/* 310 */       resultMoves[0][0][0] = 0;
/* 311 */       resultMoves[0][2][0] = 0;
/*     */     } 
/*     */     
/* 314 */     if (resultMoves[0][1][2] == 0) {
/* 315 */       resultMoves[0][0][2] = 0;
/* 316 */       resultMoves[0][2][2] = 0;
/*     */     } 
/*     */     
/* 319 */     if (resultMoves[2][1][0] == 0) {
/* 320 */       resultMoves[2][0][0] = 0;
/* 321 */       resultMoves[2][2][0] = 0;
/*     */     } 
/*     */     
/* 324 */     if (resultMoves[2][1][2] == 0) {
/* 325 */       resultMoves[2][0][2] = 0;
/* 326 */       resultMoves[2][2][2] = 0;
/*     */     } 
/*     */     
/* 329 */     if (resultMoves[1][0][0] == 0) {
/* 330 */       resultMoves[0][0][0] = 0;
/* 331 */       resultMoves[2][0][0] = 0;
/*     */     } 
/*     */     
/* 334 */     if (resultMoves[1][0][2] == 0) {
/* 335 */       resultMoves[0][0][2] = 0;
/* 336 */       resultMoves[2][0][2] = 0;
/*     */     } 
/*     */     
/* 339 */     if (resultMoves[1][2][0] == 0) {
/* 340 */       resultMoves[0][2][0] = 0;
/* 341 */       resultMoves[2][2][0] = 0;
/*     */     } 
/*     */     
/* 344 */     if (resultMoves[1][2][2] == 0) {
/* 345 */       resultMoves[0][2][2] = 0;
/* 346 */       resultMoves[2][2][2] = 0;
/*     */     } 
/*     */     
/* 349 */     return resultMoves;
/*     */   }
/*     */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-core.jar!\buildcraft\core\li\\utils\PathFinding.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */