package buildcraft.api.boards;

public interface IRedstoneBoard<T> {
  void updateBoard(T paramT);
  
  RedstoneBoardNBT<?> getNBTHandler();
}


/* Location:              C:\Users\Nikit\files\buildcraft-api-7.99.8.2.jar!\buildcraft\api\boards\IRedstoneBoard.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */