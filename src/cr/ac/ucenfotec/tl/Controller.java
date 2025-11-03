package tl;

import cr.ac.ucenfotec.bl.entities.Departamento;
import cr.ac.ucenfotec.bl.entities.Ticket;
import cr.ac.ucenfotec.bl.entities.Usuario;
import cr.ac.ucenfotec.bl.logic.GestorDepartamento;
import cr.ac.ucenfotec.bl.logic.GestorTicket;
import cr.ac.ucenfotec.bl.logic.GestorUsuario;
import cr.ac.ucenfotec.ui.UI;

import java.io.IOException;
import java.util.ArrayList;

public class Controller {
    // UI para interactuar con el usuario
    private UI interfaz = new UI();
    
    // Handlers para manejar las entidades
    private final GestorUsuario usuarioHandler = new GestorUsuario();
    private final GestorDepartamento departamentoHandler = new GestorDepartamento();
    private final GestorTicket ticketHandler = new GestorTicket();
    
    // Usuario actualmente autenticado
    private Usuario usuarioActual = null;
    private boolean isLogged = false;

    public Controller() {
    }

    // ==================== MÉTODO PRINCIPAL ====================
    
    public void start() throws IOException {
        mostrarBienvenida();
        int opcion = -1;
        do {
            mostrarMenuAcceso();
            opcion = interfaz.leerOpcion();
            procesarOpcionAcceso(opcion);
        } while (opcion != 3);
    }

    // ==================== PROCESAMIENTO DE OPCIONES ====================
    
    private void procesarOpcionAcceso(int opcion) throws IOException {
        switch (opcion) {
            case 1:
                iniciarSesion();
                break;
            case 2:
                registrarse();
                break;
            case 3:
                interfaz.imprimirMensaje("Cerrando el programa....");
                break;
            default:
                interfaz.imprimirMensaje("Opción inválida");
                break;
        }
    }

    private void procesarOpcionUsuario(int opcion) throws IOException {
        switch (opcion) {
            case 1:
                crearNuevoTicket();
                break;
            case 2:
                verMisTickets();
                break;
            case 3:
                verTodosLosTickets();
                break;
            case 4:
                verDepartamentos();
                break;
            case 5:
                verMiPerfil();
                break;
            case 6:
                cerrarSesion();
                break;
            default:
                interfaz.imprimirMensaje("Opción inválida");
                break;
        }
    }

    private void procesarOpcionSoporte(int opcion) throws IOException {
        switch (opcion) {
            case 1:
                verTodosLosTickets();
                break;
            case 2:
                verTicketsPorEstado();
                break;
            case 3:
                verTicketsPorPrioridad();
                break;
            case 4:
                actualizarEstadoTicket();
                break;
            case 5:
                verEstadisticas();
                break;
            case 6:
                verMiPerfil();
                break;
            case 7:
                cerrarSesion();
                break;
            default:
                interfaz.imprimirMensaje("Opción inválida");
                break;
        }
    }

    private void procesarOpcionAdmin(int opcion) throws IOException {
        switch (opcion) {
            case 1:
                gestionUsuarios();
                break;
            case 2:
                gestionDepartamentos();
                break;
            case 3:
                gestionTickets();
                break;
            case 4:
                verEstadisticas();
                break;
            case 5:
                verReportes();
                break;
            case 6:
                cerrarSesion();
                break;
            default:
                interfaz.imprimirMensaje("Opción inválida");
                break;
        }
    }

    // ==================== MENÚS ====================
    
    private void mostrarBienvenida() {
        interfaz.imprimirMensaje("Gestión de Tickets de Soporte");
    }

    private void mostrarMenuAcceso() {
        interfaz.imprimirMensaje("MENÚ");
        interfaz.imprimirMensaje("1. Iniciar Sesión");
        interfaz.imprimirMensaje("2. Registrarse");
        interfaz.imprimirMensaje("3. Salir");
        interfaz.imprimirMensaje("Seleccione una opción: ");
    }

    private void mostrarMenuUsuario() throws IOException {
        int opcion = -1;
        do {
            interfaz.imprimirMensaje("MENÚ USUARIO");
            interfaz.imprimirMensaje("1. Crear Nuevo Ticket");
            interfaz.imprimirMensaje("2. Ver Mis Tickets");
            interfaz.imprimirMensaje("3. Ver Todos los Tickets");
            interfaz.imprimirMensaje("4. Ver Departamentos");
            interfaz.imprimirMensaje("5. Mi Perfil");
            interfaz.imprimirMensaje("6. Cerrar Sesión");
            interfaz.imprimirMensaje("Seleccione una opción: ");
            opcion = interfaz.leerOpcion();
            procesarOpcionUsuario(opcion);
        } while (opcion != 6 && isLogged);
    }

    private void mostrarMenuSoporte() throws IOException {
        int opcion = -1;
        do {
            interfaz.imprimirMensaje("MENÚ SOPORTE");
            interfaz.imprimirMensaje("1. Ver Todos los Tickets");
            interfaz.imprimirMensaje("2. Ver Tickets por Estado");
            interfaz.imprimirMensaje("3. Ver Tickets por Prioridad");
            interfaz.imprimirMensaje("4. Actualizar Estado de Ticket");
            interfaz.imprimirMensaje("5. Ver Estadísticas");
            interfaz.imprimirMensaje("6. Mi Perfil");
            interfaz.imprimirMensaje("7. Cerrar Sesión");
            interfaz.imprimirMensaje("Seleccione una opción: ");
            opcion = interfaz.leerOpcion();
            procesarOpcionSoporte(opcion);
        } while (opcion != 7 && isLogged);
    }

    private void mostrarMenuAdmin() throws IOException {
        int opcion = -1;
        do {
            interfaz.imprimirMensaje("MENÚ ADMINISTRADOR");
            interfaz.imprimirMensaje("1. Gestión de Usuarios");
            interfaz.imprimirMensaje("2. Gestión de Departamentos");
            interfaz.imprimirMensaje("3. Gestión de Tickets");
            interfaz.imprimirMensaje("4. Ver Estadísticas");
            interfaz.imprimirMensaje("5. Reportes");
            interfaz.imprimirMensaje("6. Cerrar Sesión");
            interfaz.imprimirMensaje("Seleccione una opción: ");
            opcion = interfaz.leerOpcion();
            procesarOpcionAdmin(opcion);
        } while (opcion != 6 && isLogged);
    }

    // ==================== MÉTODOS DE AUTENTICACIÓN ====================
    
    private void iniciarSesion() throws IOException {
        interfaz.imprimirMensaje("\nINICIAR SESIÓN");
        interfaz.imprimirMensaje("Correo: ");
        String correo = interfaz.leerTexto();
        interfaz.imprimirMensaje("Contraseña: ");
        String password = interfaz.leerTexto();
        
        Usuario usuario = login(correo, password);
        if (usuario != null) {
            interfaz.imprimirMensaje("\n✓ Bienvenido/a " + usuario.getNombre() + "!");
            redirigirSegunRol();
        } else {
            interfaz.imprimirMensaje("\n❌ Credenciales incorrectas. Intente nuevamente.");
        }
    }

    private void registrarse() throws IOException {
        interfaz.imprimirMensaje("\n=== REGISTRO DE USUARIO ===");
        interfaz.imprimirMensaje("Cédula: ");
        String cedula = interfaz.leerTexto();
        interfaz.imprimirMensaje("Nombre completo: ");
        String nombre = interfaz.leerTexto();
        interfaz.imprimirMensaje("Correo: ");
        String correo = interfaz.leerTexto();
        interfaz.imprimirMensaje("Contraseña: ");
        String password = interfaz.leerTexto();
        interfaz.imprimirMensaje("Teléfono: ");
        String telefono = interfaz.leerTexto();
        
        Usuario usuario = register(cedula, nombre, correo, password, telefono);
        if (usuario != null) {
            interfaz.imprimirMensaje("\n✓ Registro exitoso! Bienvenido al sistema.");
            redirigirSegunRol();
        } else {
            interfaz.imprimirMensaje("\n❌ Error: El usuario ya existe.");
        }
    }

    private void redirigirSegunRol() throws IOException {
        if (usuarioActual == null) return;
        
        String rol = usuarioActual.getRol().toLowerCase();
        switch (rol) {
            case "admin":
                mostrarMenuAdmin();
                break;
            case "soporte":
                mostrarMenuSoporte();
                break;
            case "usuario":
            default:
                mostrarMenuUsuario();
                break;
        }
    }

    private void cerrarSesion() {
        logout();
        interfaz.imprimirMensaje("\n✓ Sesión cerrada exitosamente.");
    }

    // ==================== MÉTODOS DE FUNCIONALIDAD ====================
    
    private void crearNuevoTicket() throws IOException {
        interfaz.imprimirMensaje("\n=== CREAR NUEVO TICKET ===");
        interfaz.imprimirMensaje("ID del ticket: ");
        int id = interfaz.leerOpcion();
        interfaz.imprimirMensaje("Asunto: ");
        String asunto = interfaz.leerTexto();
        interfaz.imprimirMensaje("Descripción: ");
        String descripcion = interfaz.leerTexto();
        interfaz.imprimirMensaje("Prioridad (Baja/Media/Alta): ");
        String prioridad = interfaz.leerTexto();
        interfaz.imprimirMensaje("ID del Departamento: ");
        int departamentoId = interfaz.leerOpcion();
        
        Ticket ticket = crearTicket(id, asunto, descripcion, prioridad, departamentoId);
        if (ticket != null) {
            interfaz.imprimirMensaje("\n✓ Ticket creado exitosamente.");
        } else {
            interfaz.imprimirMensaje("\n❌ Error al crear el ticket.");
        }
    }

    private void verMisTickets() {
        interfaz.imprimirMensaje("\n=== MIS TICKETS ===");
        ArrayList<Ticket> tickets = getTicketsPorUsuario();
        if (tickets.isEmpty()) {
            interfaz.imprimirMensaje("No tiene tickets registrados.");
        } else {
            for (Ticket t : tickets) {
                interfaz.imprimirMensaje("ID: " + t.getId() + " | Asunto: " + t.getAsunto() + 
                                       " | Estado: " + t.getEstado() + " | Prioridad: " + t.getPrioridad());
            }
        }
    }

    private void verTodosLosTickets() {
        interfaz.imprimirMensaje("\n=== TODOS LOS TICKETS ===");
        ArrayList<Ticket> tickets = getAllTickets();
        if (tickets.isEmpty()) {
            interfaz.imprimirMensaje("No hay tickets registrados.");
        } else {
            for (Ticket t : tickets) {
                interfaz.imprimirMensaje("ID: " + t.getId() + " | Asunto: " + t.getAsunto() + 
                                       " | Estado: " + t.getEstado() + " | Usuario: " + t.getUsuario().getNombre());
            }
        }
    }

    private void verDepartamentos() {
        interfaz.imprimirMensaje("\n=== DEPARTAMENTOS ===");
        ArrayList<Departamento> departamentos = getAllDepartamentos();
        if (departamentos.isEmpty()) {
            interfaz.imprimirMensaje("No hay departamentos registrados.");
        } else {
            for (Departamento d : departamentos) {
                interfaz.imprimirMensaje("ID: " + d.getId() + " | Nombre: " + d.getNombre() + 
                                       " | Contacto: " + d.getContacto());
            }
        }
    }

    private void verMiPerfil() {
        if (usuarioActual != null) {
            interfaz.imprimirMensaje("\n=== MI PERFIL ===");
            interfaz.imprimirMensaje("Nombre: " + usuarioActual.getNombre());
            interfaz.imprimirMensaje("Correo: " + usuarioActual.getCorreo());
            interfaz.imprimirMensaje("Teléfono: " + usuarioActual.getTelefono());
            interfaz.imprimirMensaje("Rol: " + usuarioActual.getRol());
        }
    }

    private void verTicketsPorEstado() throws IOException {
        interfaz.imprimirMensaje("\n=== TICKETS POR ESTADO ===");
        interfaz.imprimirMensaje("Estado (Abierto/En Proceso/Cerrado): ");
        String estado = interfaz.leerTexto();
        ArrayList<Ticket> tickets = getTicketsPorEstado(estado);
        if (tickets.isEmpty()) {
            interfaz.imprimirMensaje("No hay tickets con ese estado.");
        } else {
            for (Ticket t : tickets) {
                interfaz.imprimirMensaje("ID: " + t.getId() + " | Asunto: " + t.getAsunto() + 
                                       " | Prioridad: " + t.getPrioridad());
            }
        }
    }

    private void verTicketsPorPrioridad() throws IOException {
        interfaz.imprimirMensaje("\n=== TICKETS POR PRIORIDAD ===");
        interfaz.imprimirMensaje("Prioridad (Baja/Media/Alta): ");
        String prioridad = interfaz.leerTexto();
        ArrayList<Ticket> tickets = getTicketsPorPrioridad(prioridad);
        if (tickets.isEmpty()) {
            interfaz.imprimirMensaje("No hay tickets con esa prioridad.");
        } else {
            for (Ticket t : tickets) {
                interfaz.imprimirMensaje("ID: " + t.getId() + " | Asunto: " + t.getAsunto() + 
                                       " | Estado: " + t.getEstado());
            }
        }
    }

    private void actualizarEstadoTicket() throws IOException {
        interfaz.imprimirMensaje("\n=== ACTUALIZAR ESTADO DE TICKET ===");
        interfaz.imprimirMensaje("ID del ticket: ");
        int ticketId = interfaz.leerOpcion();
        interfaz.imprimirMensaje("Nuevo estado (Abierto/En Proceso/Cerrado): ");
        String nuevoEstado = interfaz.leerTexto();
        
        if (cambiarEstadoTicket(ticketId, nuevoEstado)) {
            interfaz.imprimirMensaje("\n✓ Estado actualizado exitosamente.");
        } else {
            interfaz.imprimirMensaje("\n❌ Error al actualizar el estado.");
        }
    }

    private void verEstadisticas() {
        interfaz.imprimirMensaje("\n=== ESTADÍSTICAS ===");
        interfaz.imprimirMensaje("Tickets Abiertos: " + getNumeroTicketsAbiertos());
        interfaz.imprimirMensaje("Tickets En Proceso: " + getNumeroTicketsEnProceso());
        interfaz.imprimirMensaje("Tickets Cerrados: " + getNumeroTicketsCerrados());
    }

    private void gestionUsuarios() throws IOException {
        int opcion = -1;
        do {
            interfaz.imprimirMensaje("GESTIÓN DE USUARIOS");
            interfaz.imprimirMensaje("1. Listar Usuarios");
            interfaz.imprimirMensaje("2. Cambiar Rol de Usuario");
            interfaz.imprimirMensaje("3. Eliminar Usuario");
            interfaz.imprimirMensaje("4. Volver");
            interfaz.imprimirMensaje("Seleccione una opción: ");
            opcion = interfaz.leerOpcion();
            
            switch (opcion) {
                case 1:
                    listarUsuarios();
                    break;
                case 2:
                    cambiarRolUsuarioMenu();
                    break;
                case 3:
                    eliminarUsuarioMenu();
                    break;
                case 4:
                    break;
                default:
                    interfaz.imprimirMensaje("Opción inválida");
                    break;
            }
        } while (opcion != 4);
    }

    private void gestionDepartamentos() throws IOException {
        int opcion = -1;
        do {
            interfaz.imprimirMensaje("GESTIÓN DE DEPARTAMENTOS");
            interfaz.imprimirMensaje("1. Listar Departamentos");
            interfaz.imprimirMensaje("2. Crear Departamento");
            interfaz.imprimirMensaje("3. Modificar Departamento");
            interfaz.imprimirMensaje("4. Eliminar Departamento");
            interfaz.imprimirMensaje("5. Volver");
            interfaz.imprimirMensaje("Seleccione una opción: ");
            opcion = interfaz.leerOpcion();
            
            switch (opcion) {
                case 1:
                    verDepartamentos();
                    break;
                case 2:
                    crearDepartamentoMenu();
                    break;
                case 3:
                    modificarDepartamentoMenu();
                    break;
                case 4:
                    eliminarDepartamentoMenu();
                    break;
                case 5:
                    break;
                default:
                    interfaz.imprimirMensaje("Opción inválida");
                    break;
            }
        } while (opcion != 5);
    }

    private void gestionTickets() throws IOException {
        interfaz.imprimirMensaje("\n=== GESTIÓN DE TICKETS ===");
        verTodosLosTickets();
    }

    private void verReportes() {
        interfaz.imprimirMensaje("\n=== REPORTES ===");
        interfaz.imprimirMensaje("Funcionalidad en desarrollo...");
    }

    private void listarUsuarios() {
        interfaz.imprimirMensaje("\n=== LISTA DE USUARIOS ===");
        ArrayList<Usuario> usuarios = getAllUsuarios();
        if (usuarios.isEmpty()) {
            interfaz.imprimirMensaje("No hay usuarios registrados.");
        } else {
            for (Usuario u : usuarios) {
                interfaz.imprimirMensaje("ID: " + u.getId() + " | Nombre: " + u.getNombre() + 
                                       " | Correo: " + u.getCorreo() + " | Rol: " + u.getRol());
            }
        }
    }

    private void cambiarRolUsuarioMenu() throws IOException {
        interfaz.imprimirMensaje("\n=== CAMBIAR ROL DE USUARIO ===");
        interfaz.imprimirMensaje("ID del usuario: ");
        String usuarioId = interfaz.leerTexto();
        interfaz.imprimirMensaje("Nuevo rol (usuario/soporte/admin): ");
        String nuevoRol = interfaz.leerTexto();
        
        if (cambiarRolUsuario(usuarioId, nuevoRol)) {
            interfaz.imprimirMensaje("\n✓ Rol actualizado exitosamente.");
        } else {
            interfaz.imprimirMensaje("\n❌ Error al actualizar el rol.");
        }
    }

    private void eliminarUsuarioMenu() throws IOException {
        interfaz.imprimirMensaje("\n=== ELIMINAR USUARIO ===");
        interfaz.imprimirMensaje("ID del usuario: ");
        String usuarioId = interfaz.leerTexto();
        
        if (deleteUsuario(usuarioId)) {
            interfaz.imprimirMensaje("\n✓ Usuario eliminado exitosamente.");
        } else {
            interfaz.imprimirMensaje("\n❌ Error al eliminar el usuario.");
        }
    }

    private void crearDepartamentoMenu() throws IOException {
        interfaz.imprimirMensaje("\n=== CREAR DEPARTAMENTO ===");
        interfaz.imprimirMensaje("ID del departamento: ");
        int id = interfaz.leerOpcion();
        interfaz.imprimirMensaje("Nombre: ");
        String nombre = interfaz.leerTexto();
        interfaz.imprimirMensaje("Descripción: ");
        String descripcion = interfaz.leerTexto();
        interfaz.imprimirMensaje("Contacto: ");
        String contacto = interfaz.leerTexto();
        
        Departamento depto = crearDepartamento(id, nombre, descripcion, contacto);
        if (depto != null) {
            interfaz.imprimirMensaje("\n✓ Departamento creado exitosamente.");
        } else {
            interfaz.imprimirMensaje("\n❌ Error al crear el departamento.");
        }
    }

    private void modificarDepartamentoMenu() throws IOException {
        interfaz.imprimirMensaje("\n=== MODIFICAR DEPARTAMENTO ===");
        interfaz.imprimirMensaje("ID del departamento: ");
        int id = interfaz.leerOpcion();
        Departamento depto = getDepartamentoById(id);
        
        if (depto != null) {
            interfaz.imprimirMensaje("Nuevo nombre (actual: " + depto.getNombre() + "): ");
            String nombre = interfaz.leerTexto();
            interfaz.imprimirMensaje("Nueva descripción (actual: " + depto.getDescripcion() + "): ");
            String descripcion = interfaz.leerTexto();
            interfaz.imprimirMensaje("Nuevo contacto (actual: " + depto.getContacto() + "): ");
            String contacto = interfaz.leerTexto();
            
            depto.setNombre(nombre);
            depto.setDescripcion(descripcion);
            depto.setContacto(contacto);
            
            if (updateDepartamento(depto)) {
                interfaz.imprimirMensaje("\n✓ Departamento modificado exitosamente.");
            } else {
                interfaz.imprimirMensaje("\n❌ Error al modificar el departamento.");
            }
        } else {
            interfaz.imprimirMensaje("\n❌ Departamento no encontrado.");
        }
    }

    private void eliminarDepartamentoMenu() throws IOException {
        interfaz.imprimirMensaje("\n=== ELIMINAR DEPARTAMENTO ===");
        interfaz.imprimirMensaje("ID del departamento: ");
        String id = interfaz.leerTexto();
        
        if (deleteDepartamento(id)) {
            interfaz.imprimirMensaje("\n✓ Departamento eliminado exitosamente.");
        } else {
            interfaz.imprimirMensaje("\n❌ Error al eliminar el departamento.");
        }
    }

    // ==================== MÉTODOS DE AUTENTICACIÓN (LÓGICA) ====================
    
    public Usuario login(String correo, String password) {
        Usuario usuario = usuarioHandler.findUsuarioByCorreoAndPassword(correo, password);
        if (usuario != null) {
            this.usuarioActual = usuario;
            this.isLogged = true;
        }
        return usuario;
    }

    public Usuario register(String cedula, String nombre, String correo, String password, String telefono) {
        // Verificar si el correo ya existe
        if (usuarioHandler.findUsuarioByCorreo(correo) != null) {
            return null; // El usuario ya existe
        }

        if(usuarioHandler.findUsuarioById(cedula) != null) {
            return null;
        }
        
        Usuario usuario = usuarioHandler.addUsuario(cedula, nombre, correo, password, telefono, "usuario");
        this.usuarioActual = usuario;
        this.isLogged = true;
        return usuario;
    }

    public void logout() {
        this.usuarioActual = null;
        this.isLogged = false;
    }

    public Usuario getUsuarioActual() {
        return usuarioActual;
    }

    public boolean isLogged() {
        return isLogged;
    }

    // ==================== MÉTODOS DE USUARIO ====================
    
    public ArrayList<Usuario> getAllUsuarios() {
        return usuarioHandler.getAllUsuarios();
    }

    public Usuario getUsuarioById(String id) {
        return usuarioHandler.findUsuarioById(id);
    }

    public boolean updateUsuario(Usuario usuario) {
        return usuarioHandler.updateUsuario(usuario);
    }

    public boolean deleteUsuario(String id) {
        return usuarioHandler.deleteUsuario(id);
    }

    public boolean cambiarRolUsuario(String usuarioId, String nuevoRol) {
        Usuario usuario = usuarioHandler.findUsuarioById(usuarioId);
        if (usuario != null) {
            usuario.setRol(nuevoRol);
            return usuarioHandler.updateUsuario(usuario);
        }
        return false;
    }

    // ==================== MÉTODOS DE DEPARTAMENTO ====================
    
    public Departamento crearDepartamento(int id, String nombre, String descripcion, String contacto) {
        return departamentoHandler.addDepartamento(id, nombre, descripcion, contacto);
    }

    public ArrayList<Departamento> getAllDepartamentos() {
        return departamentoHandler.getAllDepartamentos();
    }

    public Departamento getDepartamentoById(int id) {
        return departamentoHandler.findDepartamentoById(id);
    }

    public Departamento getDepartamentoByNombre(String nombre) {
        return departamentoHandler.findDepartamentoByNombre(nombre);
    }

    public boolean updateDepartamento(Departamento departamento) {
        return departamentoHandler.updateDepartamento(departamento);
    }

    public boolean deleteDepartamento(String id) {
        return departamentoHandler.deleteDepartamento(id);
    }

    // ==================== MÉTODOS DE TICKET ====================
    
    public Ticket crearTicket(int id, String asunto, String descripcion, String prioridad, int departamentoId) {
        if (!isLogged || usuarioActual == null) {
            return null; // Usuario no autenticado
        }
        
        Departamento departamento = departamentoHandler.findDepartamentoById(departamentoId);
        if (departamento == null) {
            return null; // Departamento no encontrado
        }
        
        return ticketHandler.createTicket(id, asunto, descripcion, prioridad, usuarioActual, departamento);
    }

    public ArrayList<Ticket> getAllTickets() {
        return ticketHandler.getAllTickets();
    }

    public ArrayList<Ticket> getTicketsPorUsuario() {
        if (!isLogged || usuarioActual == null) {
            return new ArrayList<>();
        }
        return ticketHandler.getTicketsByUsuario(usuarioActual.getId());
    }

    public ArrayList<Ticket> getTicketsPorUsuarioId(String usuarioId) {
        return ticketHandler.getTicketsByUsuario(usuarioId);
    }

    public ArrayList<Ticket> getTicketsPorDepartamento(String departamentoId) {
        return ticketHandler.getTicketsByDepartamento(departamentoId);
    }

    public ArrayList<Ticket> getTicketsPorEstado(String estado) {
        return ticketHandler.getTicketsByEstado(estado);
    }

    public ArrayList<Ticket> getTicketsPorPrioridad(String prioridad) {
        return ticketHandler.getTicketsByPrioridad(prioridad);
    }

    public Ticket getTicketById(int id) {
        return ticketHandler.findTicketById(id);
    }

    public boolean updateTicket(Ticket ticket) {
        return ticketHandler.updateTicket(ticket);
    }

    public boolean cambiarEstadoTicket(int ticketId, String nuevoEstado) {
        return ticketHandler.updateEstadoTicket(ticketId, nuevoEstado);
    }

    public boolean deleteTicket(int id) {
        return ticketHandler.deleteTicket(id);
    }

    // ==================== MÉTODOS DE VALIDACIÓN Y PERMISOS ====================
    
    public boolean esAdmin() {
        return isLogged && usuarioActual != null && usuarioActual.getRol().equalsIgnoreCase("admin");
    }

    public boolean esSoporte() {
        return isLogged && usuarioActual != null && usuarioActual.getRol().equalsIgnoreCase("soporte");
    }

    public boolean esUsuario() {
        return isLogged && usuarioActual != null && usuarioActual.getRol().equalsIgnoreCase("usuario");
    }

    // ==================== MÉTODOS DE ESTADÍSTICAS ====================
    
    public int getNumeroTicketsAbiertos() {
        return ticketHandler.getTicketsByEstado("Abierto").size();
    }

    public int getNumeroTicketsEnProceso() {
        return ticketHandler.getTicketsByEstado("En Proceso").size();
    }

    public int getNumeroTicketsCerrados() {
        return ticketHandler.getTicketsByEstado("Cerrado").size();
    }

    public int getNumeroTicketsPorPrioridad(String prioridad) {
        return ticketHandler.getTicketsByPrioridad(prioridad).size();
    }
}
