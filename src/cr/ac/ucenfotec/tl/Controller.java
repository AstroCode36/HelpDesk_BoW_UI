package cr.ac.ucenfotec.tl;

import cr.ac.ucenfotec.bl.logic.GestorDepartamento;
import cr.ac.ucenfotec.bl.logic.GestorTicket;
import cr.ac.ucenfotec.bl.logic.GestorUsuario;
import cr.ac.ucenfotec.ui.UI;

import java.io.IOException;

public class Controller {
    // UI para interactuar con el usuario
    private UI interfaz = new UI();
    
    // Handlers para manejar las entidades
    private final GestorUsuario usuarioHandler = new GestorUsuario();
    private final GestorDepartamento departamentoHandler = new GestorDepartamento();
    private final GestorTicket ticketHandler = new GestorTicket();
    
    // ID del usuario actualmente autenticado
    private String usuarioActualId = null;
    private boolean isLogged = false;
    
    public Controller() {}

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
        
        String resultado = login(correo, password);
        if (resultado != null) {
            interfaz.imprimirMensaje("\n✓ Bienvenido/a!");
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
        
        String resultado = register(cedula, nombre, correo, password, telefono);

        System.out.println(resultado);

        if (resultado != null) {
            interfaz.imprimirMensaje("\n✓ Registro exitoso! Bienvenido al sistema.");
            redirigirSegunRol();
        } else {
            interfaz.imprimirMensaje("\n❌ Error: El usuario ya existe.");
        }
    }

    private void redirigirSegunRol() throws IOException {
        if (usuarioActualId == null) return;
        
        String usuarioInfo = usuarioHandler.findUsuarioById(usuarioActualId);
        if (usuarioInfo == null || usuarioInfo.equals("Usuario no encontrado")) return;
        
        String rol = "usuario"; // default
        if (usuarioInfo.toLowerCase().contains("rol: admin")) {
            rol = "admin";
        } else if (usuarioInfo.toLowerCase().contains("rol: soporte")) {
            rol = "soporte";
        }
        
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
        
        boolean exito = crearTicket(id, asunto, descripcion, prioridad, departamentoId);
        if (exito) {
            interfaz.imprimirMensaje("\n✓ Ticket creado exitosamente.");
        } else {
            interfaz.imprimirMensaje("\n❌ Error al crear el ticket.");
        }
    }

    private void verMisTickets() {
        interfaz.imprimirMensaje("\n=== MIS TICKETS ===");
        String tickets = getTicketsPorUsuario();
        if (tickets.isEmpty()) {
            interfaz.imprimirMensaje("No tiene tickets registrados.");
        } else {
            interfaz.imprimirMensaje(tickets);
        }
    }

    private void verTodosLosTickets() {
        interfaz.imprimirMensaje("\n=== TODOS LOS TICKETS ===");
        String tickets = getAllTickets();
        if (tickets.isEmpty()) {
            interfaz.imprimirMensaje("No hay tickets registrados.");
        } else {
            interfaz.imprimirMensaje(tickets);
        }
    }

    private void verDepartamentos() {
        interfaz.imprimirMensaje("\n=== DEPARTAMENTOS ===");
        String departamentos = getAllDepartamentos();
        if (departamentos.isEmpty()) {
            interfaz.imprimirMensaje("No hay departamentos registrados.");
        } else {
            interfaz.imprimirMensaje(departamentos);
        }
    }

    private void verMiPerfil() {
        if (usuarioActualId != null) {
            interfaz.imprimirMensaje("\n=== MI PERFIL ===");
            String perfil = usuarioHandler.findUsuarioById(usuarioActualId);
            interfaz.imprimirMensaje(perfil);
        }
    }

    private void verTicketsPorEstado() throws IOException {
        interfaz.imprimirMensaje("\n=== TICKETS POR ESTADO ===");
        interfaz.imprimirMensaje("Estado (Abierto/En Proceso/Cerrado): ");
        String estado = interfaz.leerTexto();
        String tickets = getTicketsPorEstado(estado);
        if (tickets.isEmpty()) {
            interfaz.imprimirMensaje("No hay tickets con ese estado.");
        } else {
            interfaz.imprimirMensaje(tickets);
        }
    }

    private void verTicketsPorPrioridad() throws IOException {
        interfaz.imprimirMensaje("\n=== TICKETS POR PRIORIDAD ===");
        interfaz.imprimirMensaje("Prioridad (Baja/Media/Alta): ");
        String prioridad = interfaz.leerTexto();
        String tickets = getTicketsPorPrioridad(prioridad);
        if (tickets.isEmpty()) {
            interfaz.imprimirMensaje("No hay tickets con esa prioridad.");
        } else {
            interfaz.imprimirMensaje(tickets);
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
        String usuarios = getAllUsuarios();
        if (usuarios.isEmpty()) {
            interfaz.imprimirMensaje("No hay usuarios registrados.");
        } else {
            interfaz.imprimirMensaje(usuarios);
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
        
        boolean exito = crearDepartamento(id, nombre, descripcion, contacto);
        if (exito) {
            interfaz.imprimirMensaje("\n✓ Departamento creado exitosamente.");
        } else {
            interfaz.imprimirMensaje("\n❌ Error al crear el departamento.");
        }
    }

    private void modificarDepartamentoMenu() throws IOException {
        interfaz.imprimirMensaje("\n=== MODIFICAR DEPARTAMENTO ===");
        interfaz.imprimirMensaje("ID del departamento: ");
        int id = interfaz.leerOpcion();
        String deptoInfo = getDepartamentoById(String.valueOf(id));
        
        if (deptoInfo != null && !deptoInfo.isEmpty()) {
            interfaz.imprimirMensaje("Departamento actual:\n" + deptoInfo);
            interfaz.imprimirMensaje("Nuevo nombre: ");
            String nombre = interfaz.leerTexto();
            interfaz.imprimirMensaje("Nueva descripción: ");
            String descripcion = interfaz.leerTexto();
            interfaz.imprimirMensaje("Nuevo contacto: ");
            String contacto = interfaz.leerTexto();
            
            if (updateDepartamento(String.valueOf(id), nombre, descripcion, contacto)) {
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
    
    public String login(String correo, String password) {
        String resultado = usuarioHandler.loginUsuario(correo, password);
        if (resultado != null && !resultado.equals("Usuario no encontrado")) {
            // Extraer el ID del usuario del toString: Usuario{id='valor', ...}
            int startIdx = resultado.indexOf("id='");
            if (startIdx != -1) {
                startIdx += 4; // Saltar "id='"
                int endIdx = resultado.indexOf("'", startIdx);
                if (endIdx != -1) {
                    this.usuarioActualId = resultado.substring(startIdx, endIdx);
                    this.isLogged = true;
                    return this.usuarioActualId;
                }
            }
        }
        return null;
    }

    public String register(String cedula, String nombre, String correo, String password, String telefono) {
        String resultado = usuarioHandler.addUsuario(cedula, nombre, correo, password, telefono, "usuario");
        if (resultado != null) {
            // Extraer el ID del usuario del toString: Usuario{id='valor', ...}
            int startIdx = resultado.indexOf("id='");
            if (startIdx != -1) {
                startIdx += 4; // Saltar "id='"
                int endIdx = resultado.indexOf("'", startIdx);
                if (endIdx != -1) {
                    this.usuarioActualId = resultado.substring(startIdx, endIdx);
                    this.isLogged = true;
                    return this.usuarioActualId;
                }
            }
        }
        return null;
    }

    public void logout() {
        this.usuarioActualId = null;
        this.isLogged = false;
    }

    public String getUsuarioActualId() {
        return usuarioActualId;
    }

    public boolean isLogged() {
        return isLogged;
    }

    // ==================== MÉTODOS DE USUARIO ====================
    
    public String getAllUsuarios() {
        return usuarioHandler.getAllUsuarios();
    }

    public String getUsuarioById(String id) {
        return usuarioHandler.findUsuarioById(id);
    }

    public boolean deleteUsuario(String id) {
        String resultado = usuarioHandler.deleteUsuario(id);
        return resultado != null && resultado.contains("exitosamente");
    }

    public boolean cambiarRolUsuario(String usuarioId, String nuevoRol) {
        // TODO: Implementar cambiarRolUsuario en GestorUsuario
        return false;
    }

    // ==================== MÉTODOS DE DEPARTAMENTO ====================
    
    public boolean crearDepartamento(int id, String nombre, String descripcion, String contacto) {
        String resultado = departamentoHandler.addDepartamento(id, nombre, descripcion, contacto);
        return resultado != null && !resultado.isEmpty();
    }

    public String getAllDepartamentos() {
        return departamentoHandler.getAllDepartamentos();
    }

    public String getDepartamentoById(String id) {
        try {
            return departamentoHandler.findDepartamentoById(Integer.parseInt(id));
        } catch (NumberFormatException e) {
            return "ID de departamento inválido";
        }
    }

    public String getDepartamentoByNombre(String nombre) {
        return departamentoHandler.findDepartamentoByNombre(nombre);
    }

    public boolean updateDepartamento(String id, String nombre, String descripcion, String contacto) {
        // TODO: Requiere crear objeto Departamento y usar updateDepartamento(Departamento)
        return false;
    }

    public boolean deleteDepartamento(String id) {
        String resultado = departamentoHandler.deleteDepartamento(id);
        return resultado != null && resultado.contains("exitosamente");
    }

    // ==================== MÉTODOS DE TICKET ====================
    
    public boolean crearTicket(int id, String asunto, String descripcion, String prioridad, int departamentoId) {
        if (!isLogged || usuarioActualId == null) {
            return false; // Usuario no autenticado
        }
        // TODO: Requiere crear objetos Usuario y Departamento para llamar a createTicket
        return false;
    }

    public String getAllTickets() {
        return ticketHandler.getAllTickets();
    }

    public String getTicketsPorUsuario() {
        if (!isLogged || usuarioActualId == null) {
            return "";
        }
        return ticketHandler.getTicketsByUsuario(usuarioActualId);
    }

    public String getTicketsPorUsuarioId(String usuarioId) {
        return ticketHandler.getTicketsByUsuario(usuarioId);
    }

    public String getTicketsPorDepartamento(String departamentoId) {
        return ticketHandler.getTicketsByDepartamento(departamentoId);
    }

    public String getTicketsPorEstado(String estado) {
        return ticketHandler.getTicketsByEstado(estado);
    }

    public String getTicketsPorPrioridad(String prioridad) {
        return ticketHandler.getTicketsByPrioridad(prioridad);
    }

    public String getTicketById(int id) {
        return ticketHandler.findTicketById(id);
    }

    public boolean cambiarEstadoTicket(int ticketId, String nuevoEstado) {
        String resultado = ticketHandler.updateEstadoTicket(ticketId, nuevoEstado);
        return resultado != null && resultado.contains("actualizado");
    }

    public boolean deleteTicket(int id) {
        String resultado = ticketHandler.deleteTicket(id);
        return resultado != null && resultado.contains("exitosamente");
    }

    // ==================== MÉTODOS DE VALIDACIÓN Y PERMISOS ====================
    
    public boolean esAdmin() {
        if (!isLogged || usuarioActualId == null) return false;
        String usuarioInfo = usuarioHandler.findUsuarioById(usuarioActualId);
        return usuarioInfo != null && usuarioInfo.toLowerCase().contains("rol: admin");
    }

    public boolean esSoporte() {
        if (!isLogged || usuarioActualId == null) return false;
        String usuarioInfo = usuarioHandler.findUsuarioById(usuarioActualId);
        return usuarioInfo != null && usuarioInfo.toLowerCase().contains("rol: soporte");
    }

    public boolean esUsuario() {
        if (!isLogged || usuarioActualId == null) return false;
        String usuarioInfo = usuarioHandler.findUsuarioById(usuarioActualId);
        return usuarioInfo != null && usuarioInfo.toLowerCase().contains("rol: usuario");
    }

    // ==================== MÉTODOS DE ESTADÍSTICAS ====================
    
    public int getNumeroTicketsAbiertos() {
        String tickets = ticketHandler.getTicketsByEstado("Abierto");
        return contarTickets(tickets);
    }

    public int getNumeroTicketsEnProceso() {
        String tickets = ticketHandler.getTicketsByEstado("En Proceso");
        return contarTickets(tickets);
    }

    public int getNumeroTicketsCerrados() {
        String tickets = ticketHandler.getTicketsByEstado("Cerrado");
        return contarTickets(tickets);
    }

    public int getNumeroTicketsPorPrioridad(String prioridad) {
        String tickets = ticketHandler.getTicketsByPrioridad(prioridad);
        return contarTickets(tickets);
    }
    
    private int contarTickets(String ticketsString) {
        if (ticketsString == null || ticketsString.isEmpty() || ticketsString.contains("No se encontraron") || ticketsString.contains("No hay")) {
            return 0;
        }
        // Contar líneas separadas por \n
        return ticketsString.split("\n\n").length;
    }
}
