import java.util.Scanner;

/*
-1. Agregar mails al gestor.
-2. Borrar mails del gestor.
-3. Mostrar mails ordenados por fecha.
-4. Permitir filtrar mails desde una fecha en adelante.
-5. Permitir filtrar mails hasta una fecha.
-6. Mostrar mails ordenados por remitente.
-7. Buscar mails por remitente.
-8. Buscar mails por palabras del texto o asunto.
*/

public class Main {

  public static void main(String[] args) throws Exception {
    // Implemetación
    System.out.println("Parcial 2");
    MailManager mailManager = new MailManager();
    Scanner scanner = new Scanner(System.in);
    int opcion = 1;
    char seguir = 's';

    while (seguir == 's' || seguir == 'S') {

      mostrarMenu();
      opcion = scanner.nextInt();

      switch (opcion) {

        case 0:
          System.exit(1);

        case 1:
          System.out.print("Nombre de archivo: ");
          String nombreArchivo = scanner.next();
          try {
            mailManager.addMail(nombreArchivo);
          } catch (Exception e) {
            System.out.println(e.getMessage());
          }
          break;

        case 2: {
          System.out.print("Ingrese ID: ");
          long id = scanner.nextLong();
          try {
            mailManager.deleteMail(id);
          } catch (Exception e) {
            System.out.println(e.getMessage());
          }
        }
        break;

        case 3: {
          try {
            mostrarArray(mailManager.getSortedByDate());
          } catch (Exception e) {
            System.out.println(e.getMessage());
          }
        }
        break;

        case 4: {
          System.out.print("Desde: ");
          scanner.nextLine();
          String desde = scanner.nextLine();
          try {
            mostrarArray(mailManager.getSortedByDatesDesde(desde));
          } catch (Exception e) {
            System.out.println("La fecha ingresada no es valida.");
          }
        }
        break;

        case 5: {
          System.out.print("Hasta: ");
          scanner.nextLine();
          String hasta = scanner.nextLine();
          try {
            mostrarArray(mailManager.getSortedByDatesHasta(hasta));
          } catch (Exception e) {
            System.out.println("La fecha ingresada no es valida.");
          }
        }
        break;

        case 6: {
          try {
            mostrarArray(mailManager.getSortedByFrom());
          } catch (Exception e) {
            System.out.println(e.getMessage());
          }
        }
        break;

        case 7: {
          String remitente;
          System.out.print("Ingresar Remitente: ");
          scanner.nextLine();
          remitente = scanner.nextLine();
          try {
            mostrarArray(mailManager.getByFrom(remitente));
          } catch (Exception e) {
            System.out.println(e.getMessage());
          }
        }
        break;

        case 8: {
          String busqueda;
          System.out.print("Ingresar String: ");
          scanner.nextLine();
          busqueda = scanner.nextLine();
          try {
            mostrarArray(mailManager.getByQuery(busqueda));
          } catch (Exception e) {
            System.out.println(e.getMessage());
          }
        }
        break;

        default:
          System.out.println("La opcion " + opcion + " no es valida.");
          break;
      }

      System.out.print("\nDesea Continuar? (S/N): ");
      seguir = scanner.next().charAt(0);
    }


  }

  public static void mostrarMenu() {
    System.out.println("Menu Principal");
    System.out.println("1. Agregar mails al gestor");
    System.out.println("2. Borrar mails del gestor");
    System.out.println("3. Mostrar mails ordenados por fecha");
    System.out.println("4. Permitir filtrar mails desde una fecha en adelante");
    System.out.println("5. Permitir filtrar mails hasta una fecha");
    System.out.println("6. Mostrar mails ordenados por remitente");
    System.out.println("7. Buscar mails por remitente");
    System.out.println("8. Buscar mails por palabras del texto o asunto");
    System.out.println("0. Salir");
    System.out.print("\nOpcion: ");
  }

  public static void mostrarArray(Email[] emails) {
    System.out.print("[");
    for (int i = 0; i < emails.length; ++i) {
      System.out.print(emails[i]);
      if ((i + 1) != emails.length) {
        System.out.print(", ");
      }
    }
    System.out.println("]");
  }

}
