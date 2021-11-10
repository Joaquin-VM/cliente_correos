public interface Iterator<T> {

  /**
   * Evalua si la iteracion tiene mas elementos.
   * @return true si la iteracion tiene mas elementos.
   */
  boolean hasNext();

  /**
   * Retorna el proximo elemento en la iteracion.
   * @return el proximo elemento en la iteracion.
   */
  T next() throws Exception;

}
