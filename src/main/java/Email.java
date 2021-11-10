/**
 * Clase con los datos de un email. Puede tener mas propiedades o metodos
 */
public class Email implements Comparable<Email> {

  private long id;
  private String from;    // Remitente del mail
  private String to;      // destinatario del mail
  private String date;    // Fecha de envio
  private String subject; // Asunto del mail
  private String content; // Contenido del mail.

  public Email() {
  }

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public String getFrom() {
    return from;
  }

  public void setFrom(String from) {
    this.from = from.trim();
  }

  public String getTo() {
    return to;
  }

  public void setTo(String to) {
    this.to = to.trim();
  }

  public String getDate() {
    return date;
  }

  public void setDate(String date) {
    this.date = date.trim();
  }

  public String getSubject() {
    return subject;
  }

  public void setSubject(String subject) {
    this.subject = subject.trim();
  }

  public String getContent() {
    return this.content;
  }

  public void setContent(String content) {
    if (this.content == null) {
      this.content = content;
    } else {
      this.content += "\n" + content;
    }
  }

  @Override
  public String toString() {
    return "Email{" +
        "id=" + id +
        ", from='" + from + '\'' +
        ", to='" + to + '\'' +
        ", date='" + date + '\'' +
        '}';
  }

  @Override
  public int compareTo(Email m) {
    return Long.compare(id, m.id);
  }

}
