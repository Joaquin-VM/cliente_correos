import java.io.RandomAccessFile;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class MailManager {

  private int id = 0;

  //n, cantidad de emails.

  //Estructuras.
  AvlTree<Par> arbolDeFechas = new AvlTree<>();
  AvlTree<Par> arbolDeRemitentes = new AvlTree<>();
  HashMap<String, AvlTree<Email>> hashPorRemitentes = new HashMap<>();
  HashMap<String, AvlTree<Email>> hashPorString = new HashMap<>();
  HashMap<Long, Email> hashPorIds = new HashMap<>();

  /**
   * Lee y guarda en el gestor todos los mails de un archivo.
   *
   * @param nombreArchivo nombre del archivo
   */
  public void addMail(String nombreArchivo) throws Exception {

    RandomAccessFile archivo = new RandomAccessFile(nombreArchivo + ".txt", "r");
    String str;

    int i = 0;
    Email email = new Email();

    archivo.readLine();

    while ((str = archivo.readLine()) != null) {

      int posFinCampo = str.indexOf(":");
      String infoCampo = str.substring(posFinCampo + 1);

      if (str.contains("date:")) {
        System.out.println("\n***********************Mail " + (++i) + "***********************");
        email.setDate(infoCampo);
      } else if (str.contains("from:")) {
        email.setFrom(infoCampo);
      } else if (str.contains("to:")) {
        email.setTo(infoCampo);
      } else if (str.contains("subject:")) {
        email.setSubject(infoCampo);
      } else if (str.trim().equals("-.-.-:-.-.-")) {
        System.out.println(email.getDate());
        addMail(email); //Agregamos el email.
        email = new Email();
      } else {
        email.setContent(str);
      }

    }

    archivo.close();

  }

  /**
   * Agrega un mail al gestor
   *
   * @param m mail a agregar
   */
  public void addMail(Email m) throws Exception { //O(log n + k)
    //Asignar id unico.
    m.setId(++id);

    hashPorIds.put(m.getId(), m); //O(1)
    addArbolFechas(m); //O(log n)
    addArbolRemitentes(m); //O(log n)
    addTHashRemitentes(m); //O(1)
    addTHashStrings(m); //O(k), k cantidad de palabras.
  }

  /**
   * Agrega un mail al arbol de fechas.
   *
   * @param m mail a agregar
   */
  private void addArbolFechas(Email m) {
    Par par = arbolDeFechas.find(new Par(m.getDate()));
    if (par == null) {
      par = new Par(m.getDate());
    }
    par.addEmail(m);
    arbolDeFechas.insert(par);
  }

  /**
   * Agrega un mail al arbol de remitentes.
   *
   * @param m mail a agregar
   */
  private void addArbolRemitentes(Email m) {
    Par par = arbolDeRemitentes.find(new Par(m.getFrom()));
    if (par == null) {
      par = new Par(m.getFrom());
    }
    par.addEmail(m);
    arbolDeRemitentes.insert(par);
  }

  /**
   * Agrega un mail al HashMap de remitentes.
   *
   * @param m mail a agregar
   */
  private void addTHashRemitentes(Email m) throws Exception {
    AvlTree<Email> arbolDeEmails;
    if (hashPorRemitentes.exist(m.getFrom())) {
      arbolDeEmails = hashPorRemitentes.get(m.getFrom());
    } else {
      arbolDeEmails = new AvlTree<>();
    }

    arbolDeEmails.insert(m);
    hashPorRemitentes.put(m.getFrom(), arbolDeEmails);
  }

  /**
   * Agrega un mail al HashMap de Strings.
   *
   * @param m mail a agregar
   */
  private void addTHashStrings(Email m) throws Exception {
    AvlTree<Email> arbolDeEmails;

    String str = null;
    String contenido = formatearString(m.getContent());
    String asunto = formatearString(m.getSubject());

    int indexEspacio;

    while (contenido != null && !contenido.isBlank()) {
      indexEspacio = contenido.indexOf(" ");

      if (indexEspacio != -1) {
        str = contenido.substring(0, indexEspacio + 1).trim();
        contenido = contenido.substring(indexEspacio + 1).trim();
      } else if (indexEspacio == -1 && contenido.length() > 0) {
        str = contenido.trim();
        contenido = "";
      }

      if (hashPorString.exist(str)) {
        arbolDeEmails = hashPorString.get(str);
      } else {
        arbolDeEmails = new AvlTree<>();
      }

      arbolDeEmails.insert(m);
      hashPorString.put(str, arbolDeEmails);

    }

    while (asunto != null && !asunto.isBlank()) {
      indexEspacio = asunto.indexOf(" ");

      if (indexEspacio != -1) {
        str = asunto.substring(0, indexEspacio + 1).trim();
        asunto = asunto.substring(indexEspacio + 1).trim();
      } else if (indexEspacio == -1 && asunto.length() > 0) {
        str = asunto.trim();
        asunto = "";
      }

      if (hashPorString.exist(str)) {
        arbolDeEmails = hashPorString.get(str);
      } else {
        arbolDeEmails = new AvlTree<>();
      }

      arbolDeEmails.insert(m);
      hashPorString.put(str, arbolDeEmails);

    }

  }

  /**
   * Elimina un mail del gestor
   *
   * @param id identificador del mail a borrar
   */
  public void deleteMail(long id) throws Exception { //O(log n + k)
    Email m = hashPorIds.get(id); //O(1)
    hashPorIds.remove(id); //O(1)
    deleteArbolFechas(m); //O(log n)
    deleteArbolRemitentes(m); //O(log n)
    deleteTHashRemitentes(m); //O(log n)
    deleteTHashStrings(m); //O(k), k cantidad de palabras.
  }

  /**
   * Elimina un mail del arbol de fechas.
   *
   * @param m mail a borrar
   */
  private void deleteArbolFechas(Email m) throws Exception {
    Par par = arbolDeFechas.find(new Par(m.getDate()));
    if (par == null) {
      throw new Exception("El Email no existe.");
    }
    par.deleteEmail(m);

    if (par.arbolEmpty()) {
      arbolDeFechas.remove(par);
    } else {
      arbolDeFechas.insert(par);
    }
  }

  /**
   * Elimina un mail del arbol de remitentes.
   *
   * @param m mail a borrar
   */
  private void deleteArbolRemitentes(Email m) throws Exception {
    Par par = arbolDeRemitentes.find(new Par(m.getFrom()));
    if (par == null) {
      throw new Exception("El Email no existe.");
    }
    par.deleteEmail(m);

    if (par.arbolEmpty()) {
      arbolDeRemitentes.remove(par);
    } else {
      arbolDeRemitentes.insert(par);
    }
  }

  /**
   * Elimina un mail del HashMap de remitentes.
   *
   * @param m mail a borrar
   */
  private void deleteTHashRemitentes(Email m) throws Exception {
    AvlTree<Email> arbolDeEmails;
    if (hashPorRemitentes.exist(m.getFrom())) {
      arbolDeEmails = hashPorRemitentes.get(m.getFrom());
    } else {
      throw new Exception("El Email no existe.");
    }

    arbolDeEmails.remove(m);

    if (arbolDeEmails.isEmpty()) {
      hashPorRemitentes.remove(m.getFrom());
    } else {
      hashPorRemitentes.put(m.getFrom(), arbolDeEmails);
    }
  }

  /**
   * Elimina un mail del HashMap de Strings.
   *
   * @param m mail a borrar
   */
  private void deleteTHashStrings(Email m) throws Exception {

    AvlTree<Email> arbolDeEmails;

    String str = null;
    String contenido = formatearString(m.getContent());
    String asunto = formatearString(m.getSubject());
    int indexEspacio;

    while (contenido != null && !contenido.isBlank()) {
      indexEspacio = contenido.indexOf(" ");

      if (indexEspacio != -1) {
        str = contenido.substring(0, indexEspacio + 1).trim();
        contenido = contenido.substring(indexEspacio + 1).trim();
      } else if (indexEspacio == -1 && contenido.length() > 0) {
        str = contenido.trim();
        contenido = "";
      }

      arbolDeEmails = hashPorString.get(str);

      arbolDeEmails.remove(m);

      if (arbolDeEmails.isEmpty()) {
        hashPorString.remove(str);
      } else {
        hashPorString.put(str, arbolDeEmails);
      }

    }

    while (asunto != null && !asunto.isBlank()) {
      indexEspacio = asunto.indexOf(" ");

      if (indexEspacio != -1) {
        str = asunto.substring(0, indexEspacio + 1).trim();
        asunto = asunto.substring(indexEspacio + 1).trim();
      } else if (indexEspacio == -1 && asunto.length() > 0) {
        str = asunto.trim();
        asunto = "";
      }

      arbolDeEmails = hashPorString.get(str);

      arbolDeEmails.remove(m);

      if (arbolDeEmails.isEmpty()) {
        hashPorString.remove(str);
      } else {
        hashPorString.put(str, arbolDeEmails);
      }

    }

  }

  /**
   * Quita los retorno de carro y caracteres especiales como acentos, etc.
   *
   * @param str String a formatear
   */
  private String formatearString(String str) {
    return str.replaceAll("\n", " ")
        .replaceAll("[\\p{InCombiningDiacriticalMarks}]", "")
        .replaceAll("[^a-zA-Z0-9\\s]", "")
        .toLowerCase();
  }

  /**
   * Devuelve una lista de mails ordenados por fecha
   *
   * @return lista de mails ordenados
   */
  public Email[] getSortedByDate() throws Exception { //O(n), n cantidad de emails.
    TreeIterator<Par> iterador = new TreeIterator<>(arbolDeFechas.getRoot());
    ArrayList<Email> emails = new ArrayList<>();

    while (iterador.hasNext()) { //O(n)
      Par par = iterador.next();
      AvlTree<Email> arbolActual = par.getValue();
      ArrayList<Email> listaActual = arbolActual.inOrderNR(arbolActual.getRoot());
      for (int i = 0; i < listaActual.size(); ++i) {
        emails.add(listaActual.get(i));
      }
    }

    return emails.toArray(new Email[emails.size()]);
  }

  /**
   * Devuelve una lista de mails ordenados por fecha que estan en el rango desde - hasta
   *
   * @param desde Fecha desde donde buscar
   * @param hasta Fecha hasta donde buscar
   * @return lista de mails ord-enados
   */
  public Email[] getSortedByDate(String desde, String hasta)
      throws Exception { //O(n), n cantidad de emails.

    ArrayList<Email> emails = new ArrayList<>();
    TreeIterator<Par> iterador = new TreeIterator<>(arbolDeFechas.getRoot());

    while (iterador.hasNext()) { //O(n)
      Par par = iterador.next();
      String fechaActual = par.getKey();
      if ((fechaActual.compareTo(desde) > 0 && fechaActual.compareTo(hasta) < 0)
          || fechaActual.equals(desde)
          || fechaActual.equals(hasta)) {
        AvlTree<Email> arbol = par.getValue();
        ArrayList<Email> listaActual = arbol.inOrderNR(arbol.getRoot());
        for (int i = 0; i < listaActual.size(); ++i) {
          emails.add(listaActual.get(i));
        }
      }
    }

    return emails.toArray(new Email[emails.size()]);
  }

  /**
   * Devuelve una lista de mails ordenados por fecha que estan en el rango desde - hasta
   *
   * @param desde Fecha desde donde buscar
   * @return lista de mails ord-enados
   */
  public Email[] getSortedByDatesDesde(String desde) throws Exception { //O(n)
    return getSortedByDate(desde, fechaToString(LocalDateTime.now()));
  }

  /**
   * Devuelve una lista de mails ordenados por fecha que estan en el rango desde - hasta
   *
   * @param hasta Fecha hasta donde buscar
   * @return lista de mails ord-enados
   */
  public Email[] getSortedByDatesHasta(String hasta) throws Exception { //O(n)
    return getSortedByDate(arbolDeFechas.findMin().getKey(), hasta);
  }

  /**
   * Convierte y devuelve una fecha en LocalDateTime en un String
   *
   * @param fecha fecha a ser convertida.
   */
  private String fechaToString(LocalDateTime fecha) {
    return fecha.toString().substring(0, 16).replace("T", " ");
  }

  /**
   * Devuelve una lista de mails ordenados por Remitente
   *
   * @return lista de mails ordenados
   */
  public Email[] getSortedByFrom() throws Exception { //O(n), n cantidad de emails.
    TreeIterator<Par> iterador = new TreeIterator<>(arbolDeRemitentes.getRoot());
    ArrayList<Email> emails = new ArrayList<>();

    while (iterador.hasNext()) { //O(n)
      Par par = iterador.next();
      AvlTree<Email> arbolActual = par.getValue();
      ArrayList<Email> listaActual = arbolActual.inOrderNR(arbolActual.getRoot());
      for (int i = 0; i < listaActual.size(); ++i) {
        emails.add(listaActual.get(i));
      }
    }

    return emails.toArray(new Email[emails.size()]);
  }

  /**
   * Devuelve una lista de mails de un determinado remitente
   *
   * @param from String con direccion del remitente
   * @return lista de mails del remitente
   */
  public Email[] getByFrom(String from)
      throws Exception { //O(k), k = cantidad de Emails del remitente.
    AvlTree<Email> arbolDeEmails = hashPorRemitentes.get(from); //O(1).
    ArrayList<Email> emails = arbolDeEmails.inOrderNR(arbolDeEmails.getRoot()); //O(k)
    return emails.toArray(new Email[emails.size()]); //O(k)
  }

  /**
   * Devuelve una lista de mails que contengan las palabras de 'query' en su asunto o en su
   * contenido
   *
   * @param query String con palabra/s a buscar
   * @return lista de mails que contienen dicha/s palabra/s
   */
  public Email[] getByQuery(String query)
      throws Exception { //O(h), h = cantidad de Emails con ese String.
    query = formatearString(query);
    AvlTree<Email> arbolDeEmails = hashPorString.get(query.toLowerCase().trim()); //O(1).
    ArrayList<Email> emails = arbolDeEmails.inOrderNR(arbolDeEmails.getRoot()); //O(h)
    return emails.toArray(new Email[emails.size()]); //O(h)
  }

}
