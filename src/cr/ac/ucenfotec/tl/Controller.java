package cr.ac.ucenfotec.tl;

import cr.ac.ucenfotec.bl.logic.GestorDepartamento;
import cr.ac.ucenfotec.bl.logic.GestorTicket;
import cr.ac.ucenfotec.bl.logic.GestorUsuario;
import cr.ac.ucenfotec.bl.logic.ClasificadorBoW;
import cr.ac.ucenfotec.ui.UI;

import java.io.IOException;

public class Controller {
    private UI interfaz = new UI();

    private final GestorUsuario usuarioHandler = new GestorUsuario();
    private final GestorDepartamento departamentoHandler = new GestorDepartamento();
    private final GestorTicket ticketHandler = new GestorTicket();

    private String usuarioActualId = null;
    private boolean isLogged = false;

    public Controller() {}

    public void start() throws IOException {
        mostrarBienvenida();
        int opcion = -1;
        do {
            mostrarMenuAcceso();
            opcion = interfaz.leerOpcion();
            procesarOpcionAcceso(opcion);
        } while (opcion != 3);
    }

    // --- PROCESAMIENTO DE MENUS ---

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
                interfaz.imprimirMensaje("Opcion invalida");
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
                // El usuario normal no debería ver TODOS los tickets, solo los suyos.
                // Sin embargo, mantengo la funcionalidad si se requiere.
                verTodosLosTickets();
                break;
            case 4:
                verDepartamentos();
                break;
            case 5:
                analizarTextoBoW();
                break;
            case 6:
                sugerirConfiguracionTicket();
                break;
            case 7:
                verMiPerfil();
                break;
            case 8:
                cerrarSesion();
                break;
            default:
                interfaz.imprimirMensaje("Opcion invalida");
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
                analizarTextoBoW();
                break;
            case 6:
                verEstadisticas();
                break;
            case 7:
                verMiPerfil();
                break;
            case 8:
                cerrarSesion();
                break;
            default:
                interfaz.imprimirMensaje("Opcion invalida");
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
                analizarTextoBoW();
                break;
            case 6:
                verReportes();
                break;
            case 7:
                cerrarSesion();
                break;
            default:
                interfaz.imprimirMensaje("Opcion invalida");
                break;
        }
    }

    // --- MENUS ---

    private void mostrarBienvenida() {
        interfaz.imprimirMensaje("==========================================");
        interfaz.imprimirMensaje("     SISTEMA HELP DESK - Bag of Words");
        interfaz.imprimirMensaje("==========================================");
    }

    private void mostrarMenuAcceso() {
        interfaz.imprimirMensaje("");
        interfaz.imprimirMensaje("--- MENU PRINCIPAL ---");
        interfaz.imprimirMensaje("1. Iniciar Sesion");
        interfaz.imprimirMensaje("2. Registrarse");
        interfaz.imprimirMensaje("3. Salir");
        interfaz.imprimirMensaje("Seleccione una opcion: ");
    }

    private void mostrarMenuUsuario() throws IOException {
        int opcion = -1;
        do {
            interfaz.imprimirMensaje("");
            interfaz.imprimirMensaje("--- MENU USUARIO ---");
            interfaz.imprimirMensaje("1. Crear Nuevo Ticket");
            interfaz.imprimirMensaje("2. Ver Mis Tickets");
            interfaz.imprimirMensaje("3. Ver Todos los Tickets"); // Mantenido, aunque no recomendable
            interfaz.imprimirMensaje("4. Ver Departamentos");
            interfaz.imprimirMensaje("5. Analizar Texto (Bag of Words)");
            interfaz.imprimirMensaje("6. Sugerir Configuracion Ticket");
            interfaz.imprimirMensaje("7. Mi Perfil");
            interfaz.imprimirMensaje("8. Cerrar Sesion");
            interfaz.imprimirMensaje("Seleccione una opcion: ");
            opcion = interfaz.leerOpcion();
            procesarOpcionUsuario(opcion);
        } while (opcion != 8 && isLogged);
    }

    private void mostrarMenuSoporte() throws IOException {
        int opcion = -1;
        do {
            interfaz.imprimirMensaje("");
            interfaz.imprimirMensaje("--- MENU SOPORTE ---");
            interfaz.imprimirMensaje("1. Ver Todos los Tickets");
            interfaz.imprimirMensaje("2. Ver Tickets por Estado");
            interfaz.imprimirMensaje("3. Ver Tickets por Prioridad");
            interfaz.imprimirMensaje("4. Actualizar Estado de Ticket");
            interfaz.imprimirMensaje("5. Analizar Texto (Bag of Words)");
            interfaz.imprimirMensaje("6. Ver Estadisticas");
            interfaz.imprimirMensaje("7. Mi Perfil");
            interfaz.imprimirMensaje("8. Cerrar Sesion");
            interfaz.imprimirMensaje("Seleccione una opcion: ");
            opcion = interfaz.leerOpcion();
            procesarOpcionSoporte(opcion);
        } while (opcion != 8 && isLogged);
    }

    private void mostrarMenuAdmin() throws IOException {
        int opcion = -1;
        do {
            interfaz.imprimirMensaje("");
            interfaz.imprimirMensaje("--- MENU ADMINISTRADOR ---");
            interfaz.imprimirMensaje("1. Gestion de Usuarios");
            interfaz.imprimirMensaje("2. Gestion de Departamentos");
            interfaz.imprimirMensaje("3. Gestion de Tickets");
            interfaz.imprimirMensaje("4. Ver Estadisticas");
            interfaz.imprimirMensaje("5. Analizar Texto (Bag of Words)");
            interfaz.imprimirMensaje("6. Reportes");
            interfaz.imprimirMensaje("7. Cerrar Sesion");
            interfaz.imprimirMensaje("Seleccione una opcion: ");
            opcion = interfaz.leerOpcion();
            procesarOpcionAdmin(opcion);
        } while (opcion != 7 && isLogged);
    }

    // --- ACCESO Y REDIRECCION ---

    private void iniciarSesion() throws IOException {
        interfaz.imprimirMensaje("");
        interfaz.imprimirMensaje("=== INICIAR SESION ===");
        interfaz.imprimirMensaje("Correo: ");
        String correo = interfaz.leerTexto();
        interfaz.imprimirMensaje("Contrasena: ");
        String password = interfaz.leerTexto();

        String resultado = login(correo, password);
        if (resultado != null) {
            interfaz.imprimirMensaje("");
            interfaz.imprimirMensaje("Bienvenido/a!");
            redirigirSegunRol();
        } else {
            interfaz.imprimirMensaje("");
            interfaz.imprimirMensaje("Credenciales incorrectas. Intente nuevamente.");
        }
    }

    private void registrarse() throws IOException {
        interfaz.imprimirMensaje("");
        interfaz.imprimirMensaje("=== REGISTRO DE USUARIO ===");
        interfaz.imprimirMensaje("Cedula: ");
        String cedula = interfaz.leerTexto();
        interfaz.imprimirMensaje("Nombre completo: ");
        String nombre = interfaz.leerTexto();
        interfaz.imprimirMensaje("Correo: ");
        String correo = interfaz.leerTexto();
        interfaz.imprimirMensaje("Contrasena: ");
        String password = interfaz.leerTexto();
        interfaz.imprimirMensaje("Telefono: ");
        String telefono = interfaz.leerTexto();

        String resultado = register(cedula, nombre, correo, password, telefono);

        if (resultado != null) {
            interfaz.imprimirMensaje("");
            interfaz.imprimirMensaje("Registro exitoso! Bienvenido al sistema.");
            redirigirSegunRol();
        } else {
            interfaz.imprimirMensaje("");
            interfaz.imprimirMensaje("Error: El usuario ya existe o hubo un problema.");
        }
    }

    private void redirigirSegunRol() throws IOException {
        if (usuarioActualId == null) return;

        String usuarioInfo = usuarioHandler.findUsuarioById(usuarioActualId);
        if (usuarioInfo == null || usuarioInfo.equals("Usuario no encontrado")) return;

        String rol = "usuario";

        // NOTA: Asumo que el GestorUsuario.findUsuarioById devuelve una cadena con "rol: admin" o "rol: soporte"
        if (usuarioInfo.toLowerCase().contains("rol: admin") || usuarioInfo.toLowerCase().contains("'admin'")) {
            rol = "admin";
        } else if (usuarioInfo.toLowerCase().contains("rol: soporte") || usuarioInfo.toLowerCase().contains("'soporte'")) {
            rol = "soporte";
        }

        this.isLogged = true; // Aseguramos que la sesión se marca como iniciada antes de redirigir

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
        interfaz.imprimirMensaje("");
        interfaz.imprimirMensaje("Sesion cerrada exitosamente.");
    }

    // --- ACCIONES DE TICKETS (Usuario/Soporte/Admin) ---

    private void crearNuevoTicket() throws IOException {
        if (!isLogged || usuarioActualId == null) {
            interfaz.imprimirMensaje("Debe iniciar sesion para crear un ticket");
            return;
        }

        interfaz.imprimirMensaje("");
        interfaz.imprimirMensaje("=== CREAR NUEVO TICKET ===");

        String usuarioInfo = usuarioHandler.findUsuarioById(usuarioActualId);
        if (usuarioInfo.equals("Usuario no encontrado")) {
            interfaz.imprimirMensaje("Error: Usuario no valido");
            return;
        }

        interfaz.imprimirMensaje("ID del ticket: ");
        int id = interfaz.leerOpcion();
        interfaz.imprimirMensaje("Asunto: ");
        String asunto = interfaz.leerTexto();
        interfaz.imprimirMensaje("Descripcion: ");
        String descripcion = interfaz.leerTexto();

        // 1. ANÁLISIS BoW
        ClasificadorBoW clasificador = new ClasificadorBoW();
        ClasificadorBoW.AnalisisTicket analisis = clasificador.analizarTicket(asunto, descripcion);

        interfaz.imprimirMensaje("");
        interfaz.imprimirMensaje("ANALISIS AUTOMATICO DETECTADO:");
        interfaz.imprimirMensaje("   Emocion: " + analisis.categoriaEmocional);
        interfaz.imprimirMensaje("   Categoria: " + analisis.categoriaTecnica);
        interfaz.imprimirMensaje("   Prioridad sugerida: " + analisis.prioridadSugerida);
        interfaz.imprimirMensaje("   Departamento sugerido: " + analisis.departamentoSugerido);

        // 2. SOLICITUD DE CONFIRMACIÓN / OVERRIDE
        interfaz.imprimirMensaje("Prioridad (Baja/Media/Alta) [" + analisis.prioridadSugerida + "]: ");
        String prioridadInput = interfaz.leerTexto();
        String prioridad = prioridadInput.isEmpty() ? analisis.prioridadSugerida : prioridadInput;

        interfaz.imprimirMensaje("ID del Departamento [" + analisis.departamentoSugerido + "]: ");
        String deptoInput = interfaz.leerTexto();
        int departamentoId;

        if (deptoInput.isEmpty()) {
            departamentoId = analisis.departamentoSugerido;
        } else {
            try {
                departamentoId = Integer.parseInt(deptoInput);
            } catch (NumberFormatException e) {
                interfaz.imprimirMensaje("ID de departamento inválido. Usando sugerido: " + analisis.departamentoSugerido);
                departamentoId = analisis.departamentoSugerido;
            }
        }

        // 3. CREACIÓN DEL TICKET
        boolean exito = crearTicket(id, asunto, descripcion, prioridad, departamentoId);

        if (exito) {
            interfaz.imprimirMensaje("");
            interfaz.imprimirMensaje("Ticket creado exitosamente con analisis automatico");
        } else {
            interfaz.imprimirMensaje("");
            interfaz.imprimirMensaje("Error al crear el ticket");
        }
    }

    private void verMisTickets() {
        interfaz.imprimirMensaje("");
        interfaz.imprimirMensaje("=== MIS TICKETS ===");
        String tickets = getTicketsPorUsuario();
        if (tickets == null || tickets.isEmpty() || tickets.contains("No se encontraron")) {
            interfaz.imprimirMensaje("No tiene tickets registrados.");
        } else {
            interfaz.imprimirMensaje(tickets);
        }
    }

    private void verTodosLosTickets() {
        interfaz.imprimirMensaje("");
        interfaz.imprimirMensaje("=== TODOS LOS TICKETS ===");
        String tickets = getAllTickets();
        if (tickets == null || tickets.isEmpty() || tickets.contains("No hay")) {
            interfaz.imprimirMensaje("No hay tickets registrados.");
        } else {
            interfaz.imprimirMensaje(tickets);
        }
    }

    private void verDepartamentos() {
        interfaz.imprimirMensaje("");
        interfaz.imprimirMensaje("=== DEPARTAMENTOS ===");
        String departamentos = getAllDepartamentos();
        if (departamentos == null || departamentos.isEmpty() || departamentos.contains("No hay")) {
            interfaz.imprimirMensaje("No hay departamentos registrados.");
        } else {
            interfaz.imprimirMensaje(departamentos);
        }
    }

    private void verMiPerfil() {
        if (usuarioActualId != null) {
            interfaz.imprimirMensaje("");
            interfaz.imprimirMensaje("=== MI PERFIL ===");
            String perfil = usuarioHandler.findUsuarioById(usuarioActualId);
            interfaz.imprimirMensaje(perfil);
        }
    }

    private void verTicketsPorEstado() throws IOException {
        interfaz.imprimirMensaje("");
        interfaz.imprimirMensaje("=== TICKETS POR ESTADO ===");
        interfaz.imprimirMensaje("Estado (Abierto/En Proceso/Cerrado): ");
        String estado = interfaz.leerTexto();
        String tickets = getTicketsPorEstado(estado);
        if (tickets == null || tickets.isEmpty() || tickets.contains("No se encontraron")) {
            interfaz.imprimirMensaje("No hay tickets con ese estado.");
        } else {
            interfaz.imprimirMensaje(tickets);
        }
    }

    private void verTicketsPorPrioridad() throws IOException {
        interfaz.imprimirMensaje("");
        interfaz.imprimirMensaje("=== TICKETS POR PRIORIDAD ===");
        interfaz.imprimirMensaje("Prioridad (Baja/Media/Alta): ");
        String prioridad = interfaz.leerTexto();
        String tickets = getTicketsPorPrioridad(prioridad);
        if (tickets == null || tickets.isEmpty() || tickets.contains("No se encontraron")) {
            interfaz.imprimirMensaje("No hay tickets con esa prioridad.");
        } else {
            interfaz.imprimirMensaje(tickets);
        }
    }

    private void actualizarEstadoTicket() throws IOException {
        interfaz.imprimirMensaje("");
        interfaz.imprimirMensaje("=== ACTUALIZAR ESTADO DE TICKET ===");
        interfaz.imprimirMensaje("ID del ticket: ");
        int ticketId = interfaz.leerOpcion();
        interfaz.imprimirMensaje("Nuevo estado (Abierto/En Proceso/Cerrado): ");
        String nuevoEstado = interfaz.leerTexto();

        if (cambiarEstadoTicket(ticketId, nuevoEstado)) {
            interfaz.imprimirMensaje("");
            interfaz.imprimirMensaje("Estado actualizado exitosamente.");
        } else {
            interfaz.imprimirMensaje("");
            interfaz.imprimirMensaje("Error al actualizar el estado. Revise el ID y el estado.");
        }
    }

    private void verEstadisticas() {
        interfaz.imprimirMensaje("");
        interfaz.imprimirMensaje("=== ESTADISTICAS ===");
        int abiertos = getNumeroTicketsAbiertos();
        int enProceso = getNumeroTicketsEnProceso();
        int cerrados = getNumeroTicketsCerrados();
        int total = abiertos + enProceso + cerrados;

        interfaz.imprimirMensaje("Tickets Abiertos: " + abiertos);
        interfaz.imprimirMensaje("Tickets En Proceso: " + enProceso);
        interfaz.imprimirMensaje("Tickets Cerrados: " + cerrados);
        interfaz.imprimirMensaje("Total de Tickets: " + total);
    }

    private void analizarTextoBoW() throws IOException {
        interfaz.imprimirMensaje("");
        interfaz.imprimirMensaje("=== ANALISIS BAG OF WORDS ===");
        interfaz.imprimirMensaje("Ingrese el texto a analizar: ");
        String texto = interfaz.leerTexto();

        ClasificadorBoW clasificador = new ClasificadorBoW();
        // Solo pasamos el texto como asunto, la descripción vacía es asumida
        ClasificadorBoW.AnalisisTicket analisis = clasificador.analizarTicket(texto, "");

        interfaz.imprimirMensaje("");
        interfaz.imprimirMensaje("RESULTADOS DEL ANALISIS:");
        interfaz.imprimirMensaje("   Categoria Emocional: " + analisis.categoriaEmocional);
        interfaz.imprimirMensaje("   Categoria Tecnica: " + analisis.categoriaTecnica);
        interfaz.imprimirMensaje("   Prioridad Sugerida: " + analisis.prioridadSugerida);
        interfaz.imprimirMensaje("   Departamento Sugerido ID: " + analisis.departamentoSugerido);
        interfaz.imprimirMensaje("   Departamento Sugerido Nombre: " + getNombreDepartamento(analisis.departamentoSugerido));
    }

    private void sugerirConfiguracionTicket() throws IOException {
        interfaz.imprimirMensaje("");
        interfaz.imprimirMensaje("=== SUGERENCIA AUTOMATICA DE TICKET ===");
        interfaz.imprimirMensaje("Asunto del problema: ");
        String asunto = interfaz.leerTexto();
        interfaz.imprimirMensaje("Descripcion detallada: ");
        String descripcion = interfaz.leerTexto();

        ClasificadorBoW clasificador = new ClasificadorBoW();
        ClasificadorBoW.AnalisisTicket analisis = clasificador.analizarTicket(asunto, descripcion);

        interfaz.imprimirMensaje("");
        interfaz.imprimirMensaje("CONFIGURACION SUGERIDA:");
        interfaz.imprimirMensaje("   Prioridad: " + analisis.prioridadSugerida);
        interfaz.imprimirMensaje("   Departamento ID: " + analisis.departamentoSugerido);
        interfaz.imprimirMensaje("   Departamento Nombre: " + getNombreDepartamento(analisis.departamentoSugerido));
        interfaz.imprimirMensaje("   Categoria Emocional: " + analisis.categoriaEmocional);
        interfaz.imprimirMensaje("   Categoria Tecnica: " + analisis.categoriaTecnica);
    }

    private String getNombreDepartamento(int departamentoId) {
        // En una aplicación real, esto consultaría al GestorDepartamento
        // Lo mantengo para simular el nombre
        switch (departamentoId) {
            case 1: return "Soporte Tecnico";
            case 2: return "Recursos Humanos";
            case 3: return "Sistemas";
            case 4: return "Administracion";
            default: return "General/Desconocido";
        }
    }

    // --- ACCIONES DE GESTIÓN (Admin) ---

    private void gestionUsuarios() throws IOException {
        int opcion = -1;
        do {
            interfaz.imprimirMensaje("");
            interfaz.imprimirMensaje("--- GESTION DE USUARIOS ---");
            interfaz.imprimirMensaje("1. Listar Usuarios");
            interfaz.imprimirMensaje("2. Cambiar Rol de Usuario");
            interfaz.imprimirMensaje("3. Eliminar Usuario");
            interfaz.imprimirMensaje("4. Volver");
            interfaz.imprimirMensaje("Seleccione una opcion: ");
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
                    interfaz.imprimirMensaje("Opcion invalida");
                    break;
            }
        } while (opcion != 4);
    }

    private void gestionDepartamentos() throws IOException {
        int opcion = -1;
        do {
            interfaz.imprimirMensaje("");
            interfaz.imprimirMensaje("--- GESTION DE DEPARTAMENTOS ---");
            interfaz.imprimirMensaje("1. Listar Departamentos");
            interfaz.imprimirMensaje("2. Crear Departamento");
            interfaz.imprimirMensaje("3. Modificar Departamento");
            interfaz.imprimirMensaje("4. Eliminar Departamento");
            interfaz.imprimirMensaje("5. Volver");
            interfaz.imprimirMensaje("Seleccione una opcion: ");
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
                    interfaz.imprimirMensaje("Opcion invalida");
                    break;
            }
        } while (opcion != 5);
    }

    private void gestionTickets() {
        interfaz.imprimirMensaje("");
        interfaz.imprimirMensaje("=== GESTION DE TICKETS (Todos) ===");
        verTodosLosTickets();
        // Se podría añadir aquí: buscar por ID, cambiar prioridad, eliminar, etc.
    }

    private void verReportes() {
        interfaz.imprimirMensaje("");
        interfaz.imprimirMensaje("=== REPORTES ===");
        interfaz.imprimirMensaje("Funcionalidad en desarrollo (ej. Tickets por mes, top departamentos).");
    }

    // --- ACCIONES DE GESTIÓN DE USUARIOS (Admin) ---

    private void listarUsuarios() {
        interfaz.imprimirMensaje("");
        interfaz.imprimirMensaje("=== LISTA DE USUARIOS ===");
        String usuarios = getAllUsuarios();
        if (usuarios == null || usuarios.isEmpty() || usuarios.contains("No hay")) {
            interfaz.imprimirMensaje("No hay usuarios registrados.");
        } else {
            interfaz.imprimirMensaje(usuarios);
        }
    }

    private void cambiarRolUsuarioMenu() throws IOException {
        interfaz.imprimirMensaje("");
        interfaz.imprimirMensaje("=== CAMBIAR ROL DE USUARIO ===");
        interfaz.imprimirMensaje("ID o Cédula del usuario a modificar: ");
        String usuarioId = interfaz.leerTexto();
        interfaz.imprimirMensaje("Nuevo rol (usuario/soporte/admin): ");
        String nuevoRol = interfaz.leerTexto();

        if (cambiarRolUsuario(usuarioId, nuevoRol)) {
            interfaz.imprimirMensaje("");
            interfaz.imprimirMensaje("Rol actualizado exitosamente.");
        } else {
            interfaz.imprimirMensaje("");
            interfaz.imprimirMensaje("Error al actualizar el rol. Revise el ID y el rol.");
        }
    }

    private void eliminarUsuarioMenu() throws IOException {
        interfaz.imprimirMensaje("");
        interfaz.imprimirMensaje("=== ELIMINAR USUARIO ===");
        interfaz.imprimirMensaje("ID o Cédula del usuario a eliminar: ");
        String usuarioId = interfaz.leerTexto();

        if (deleteUsuario(usuarioId)) {
            interfaz.imprimirMensaje("");
            interfaz.imprimirMensaje("Usuario eliminado exitosamente.");
        } else {
            interfaz.imprimirMensaje("");
            interfaz.imprimirMensaje("Error al eliminar el usuario. Revise el ID.");
        }
    }

    // --- ACCIONES DE GESTIÓN DE DEPARTAMENTOS (Admin) ---

    private void crearDepartamentoMenu() throws IOException {
        interfaz.imprimirMensaje("");
        interfaz.imprimirMensaje("=== CREAR DEPARTAMENTO ===");
        interfaz.imprimirMensaje("ID del departamento: ");
        int id = interfaz.leerOpcion();
        interfaz.imprimirMensaje("Nombre: ");
        String nombre = interfaz.leerTexto();
        interfaz.imprimirMensaje("Descripcion: ");
        String descripcion = interfaz.leerTexto();
        interfaz.imprimirMensaje("Contacto (ej: correo): ");
        String contacto = interfaz.leerTexto();

        boolean exito = crearDepartamento(id, nombre, descripcion, contacto);
        if (exito) {
            interfaz.imprimirMensaje("");
            interfaz.imprimirMensaje("Departamento creado exitosamente.");
        } else {
            interfaz.imprimirMensaje("");
            interfaz.imprimirMensaje("Error al crear el departamento. Puede que el ID ya exista.");
        }
    }

    private void modificarDepartamentoMenu() throws IOException {
        interfaz.imprimirMensaje("");
        interfaz.imprimirMensaje("=== MODIFICAR DEPARTAMENTO ===");
        interfaz.imprimirMensaje("ID del departamento a modificar: ");
        int id = interfaz.leerOpcion();
        String deptoInfo = getDepartamentoById(String.valueOf(id));

        if (deptoInfo != null && !deptoInfo.isEmpty() && !deptoInfo.contains("no encontrado")) {
            interfaz.imprimirMensaje("Departamento actual:\n" + deptoInfo);
            interfaz.imprimirMensaje("Nuevo nombre (dejar vacío para no cambiar): ");
            String nombre = interfaz.leerTexto();
            interfaz.imprimirMensaje("Nueva descripcion (dejar vacío para no cambiar): ");
            String descripcion = interfaz.leerTexto();
            interfaz.imprimirMensaje("Nuevo contacto (dejar vacío para no cambiar): ");
            String contacto = interfaz.leerTexto();

            // Lógica para no enviar cadenas vacías al BL si no se desea modificar
            // En una implementación real, se obtendrían los valores actuales y se reemplazarían solo si se ingresa un nuevo valor
            if (updateDepartamento(String.valueOf(id), nombre, descripcion, contacto)) {
                interfaz.imprimirMensaje("");
                interfaz.imprimirMensaje("Departamento modificado exitosamente.");
            } else {
                interfaz.imprimirMensaje("");
                interfaz.imprimirMensaje("Error al modificar el departamento.");
            }
        } else {
            interfaz.imprimirMensaje("");
            interfaz.imprimirMensaje("Departamento no encontrado.");
        }
    }

    private void eliminarDepartamentoMenu() throws IOException {
        interfaz.imprimirMensaje("");
        interfaz.imprimirMensaje("=== ELIMINAR DEPARTAMENTO ===");
        interfaz.imprimirMensaje("ID del departamento a eliminar: ");
        String id = interfaz.leerTexto();

        if (deleteDepartamento(id)) {
            interfaz.imprimirMensaje("");
            interfaz.imprimirMensaje("Departamento eliminado exitosamente.");
        } else {
            interfaz.imprimirMensaje("");
            interfaz.imprimirMensaje("Error al eliminar el departamento. Revise el ID.");
        }
    }

    // --- MÉTODOS DE LA LÓGICA DE NEGOCIO (simulados por llamada a Gestor) ---

    // --- Usuario Handlers ---

    public String login(String correo, String password) {
        String resultado = usuarioHandler.loginUsuario(correo, password);
        if (resultado != null && !resultado.equals("Usuario no encontrado")) {
            String id = extraerIdUsuario(resultado);
            if (id != null) {
                this.usuarioActualId = id;
                this.isLogged = true;
                return this.usuarioActualId;
            }
        }
        return null;
    }

    public String register(String cedula, String nombre, String correo, String password, String telefono) {
        // Por defecto, se registra con rol 'usuario'
        String resultado = usuarioHandler.addUsuario(cedula, nombre, correo, password, telefono, "usuario");
        if (resultado != null && !resultado.contains("Error")) {
            String id = extraerIdUsuario(resultado);
            if (id != null) {
                this.usuarioActualId = id;
                this.isLogged = true;
                return this.usuarioActualId;
            }
        }
        return null;
    }

    public void logout() {
        this.usuarioActualId = null;
        this.isLogged = false;
    }

    // Método auxiliar para extraer ID, se usa la cédula como fallback
    private String extraerIdUsuario(String usuarioInfo) {
        // Intenta extraer 'id=' o 'id=' o 'cedula='
        String[] prefixes = {"id='", "id=\"", "cedula='", "cedula=\""};
        for (String prefix : prefixes) {
            if (usuarioInfo.contains(prefix)) {
                int start = usuarioInfo.indexOf(prefix) + prefix.length();
                int end = usuarioInfo.indexOf(prefix.substring(prefix.length() - 1), start);
                if (end > start) {
                    return usuarioInfo.substring(start, end);
                }
            }
        }
        return null;
    }

    public String getAllUsuarios() {
        return usuarioHandler.getAllUsuarios();
    }

    public boolean deleteUsuario(String id) {
        String resultado = usuarioHandler.deleteUsuario(id);
        return resultado != null && resultado.contains("exitosamente");
    }

    // IMPLEMENTACIÓN AGREGADA
    public boolean cambiarRolUsuario(String usuarioId, String nuevoRol) {
        // Asumiendo que GestorUsuario.updateRolUsuario devuelve un String
        String resultado = usuarioHandler.updateRolUsuario(usuarioId, nuevoRol);
        return resultado != null && resultado.contains("exitosamente");
    }

    // --- Departamento Handlers ---

    public boolean crearDepartamento(int id, String nombre, String descripcion, String contacto) {
        String resultado = departamentoHandler.addDepartamento(id, nombre, descripcion, contacto);
        return resultado != null && !resultado.isEmpty() && !resultado.contains("Error");
    }

    public String getAllDepartamentos() {
        return departamentoHandler.getAllDepartamentos();
    }

    public String getDepartamentoById(String id) {
        try {
            return departamentoHandler.findDepartamentoById(Integer.parseInt(id));
        } catch (NumberFormatException e) {
            return "ID de departamento invalido";
        }
    }

    public String getDepartamentoByNombre(String nombre) {
        return departamentoHandler.findDepartamentoByNombre(nombre);
    }

    // IMPLEMENTACIÓN AGREGADA
    public boolean updateDepartamento(String id, String nombre, String descripcion, String contacto) {
        try {
            int deptoId = Integer.parseInt(id);
            // Asumiendo que GestorDepartamento.updateDepartamento devuelve un String de éxito
            String resultado = departamentoHandler.updateDepartamento(deptoId, nombre, descripcion, contacto);
            return resultado != null && resultado.contains("exitosamente");
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public boolean deleteDepartamento(String id) {
        String resultado = departamentoHandler.deleteDepartamento(id);
        return resultado != null && resultado.contains("exitosamente");
    }

    // --- Ticket Handlers ---

    public boolean crearTicket(int id, String asunto, String descripcion, String prioridad, int departamentoId) {
        if (!isLogged || usuarioActualId == null) {
            return false;
        }

        try {
            // Asumo que createTicket espera usuarioActualId como String y departamentoId como String
            String resultado = ticketHandler.createTicket(id, asunto, descripcion, prioridad, usuarioActualId, String.valueOf(departamentoId));
            return resultado != null && !resultado.contains("Error");

        } catch (Exception e) {
            interfaz.imprimirMensaje("Excepción al crear el ticket: " + e.getMessage());
            return false;
        }
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

    // IMPLEMENTACIÓN AGREGADA
    public String getTicketsPorEstado(String estado) {
        return ticketHandler.getTicketsByEstado(estado);
    }

    // IMPLEMENTACIÓN AGREGADA
    public String getTicketsPorPrioridad(String prioridad) {
        return ticketHandler.getTicketsByPrioridad(prioridad);
    }

    // IMPLEMENTACIÓN AGREGADA
    public boolean cambiarEstadoTicket(int ticketId, String nuevoEstado) {
        // Asumo que GestorTicket.updateTicketEstado(int id, String estado) devuelve un String
        String resultado = ticketHandler.updateTicketEstado(ticketId, nuevoEstado);
        return resultado != null && resultado.contains("exitosamente");
    }

    // IMPLEMENTACIÓN AGREGADA
    public int getNumeroTicketsAbiertos() {
        // Asumo que GestorTicket tiene un método para contar
        return ticketHandler.countTicketsByEstado("Abierto");
    }

    // IMPLEMENTACIÓN AGREGADA
    public int getNumeroTicketsEnProceso() {
        return ticketHandler.countTicketsByEstado("En Proceso");
    }

    // IMPLEMENTACIÓN AGREGADA
    public int getNumeroTicketsCerrados() {
        return ticketHandler.countTicketsByEstado("Cerrado");
    }

    // --- Getters auxiliares ---

    public String getUsuarioActualId() {
        return usuarioActualId;
    }

    public boolean isLogged() {
        return isLogged;
    }
}