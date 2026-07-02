package Pruebas;

import Dao.DaoCliente;
import Dao.DaoEntrega;
import Dao.DaoPedido;
import Dao.DaoUsuario;
import Dao.DaoVehiculo;
import Modelo.Cliente;
import Modelo.Entrega;
import Modelo.Pedido;
import Modelo.Usuario;
import Modelo.Vehiculo;

public class PruebaDAO {

    public static void main(String[] args) {

        DaoUsuario daoUsuario = new DaoUsuario();
        DaoCliente daoCliente = new DaoCliente();
        DaoVehiculo daoVehiculo = new DaoVehiculo();
        DaoPedido daoPedido = new DaoPedido();
        DaoEntrega daoEntrega = new DaoEntrega();

        System.out.println("===== INICIO DE PRUEBA DAO =====");

        /*
         * Borramos primero por si quedaron datos antiguos.
         * El orden importa:
         * primero entrega, luego pedido, luego vehículo y cliente.
         */
        daoEntrega.eliminar(9001);
        daoPedido.eliminar(9001);
        daoVehiculo.eliminar(9001);
        daoCliente.eliminar(9001);

        // =========================
        // 1. PROBAR USUARIO
        // =========================
        String usuarioPrueba = "test" + System.currentTimeMillis();

        Usuario usuario = new Usuario(
                0,
                "Usuario Prueba",
                usuarioPrueba,
                "1234",
                "OPERADOR"
        );

        boolean usuarioInsertado = daoUsuario.insertar(usuario);
        System.out.println("Usuario insertado: " + usuarioInsertado);

        Usuario usuarioLogin = daoUsuario.validarLogin(usuarioPrueba, "1234");

        if (usuarioLogin != null) {
            System.out.println("Login usuario prueba OK: " + usuarioLogin.getNombre());
        } else {
            System.out.println("Login usuario prueba FALLÓ");
        }

        Usuario admin = daoUsuario.validarLogin("admin", "12345");

        if (admin != null) {
            System.out.println("Login admin OK: " + admin.getNombre());
        } else {
            System.out.println("Login admin FALLÓ");
        }

        // =========================
        // 2. PROBAR CLIENTE
        // =========================
        Cliente cliente = new Cliente(
                9001,
                "Cliente Prueba",
                20,
                "999999999",
                "Av. Prueba 123"
        );

        boolean clienteInsertado = daoCliente.insertar(cliente);
        System.out.println("Cliente insertado: " + clienteInsertado);

        System.out.println("Clientes registrados: " + daoCliente.listar().size());

        cliente.setNombre("Cliente Modificado");
        cliente.setEdad(21);
        cliente.setTelefono("988888888");
        cliente.setDireccion("Calle Modificada 456");

        boolean clienteModificado = daoCliente.modificar(cliente);
        System.out.println("Cliente modificado: " + clienteModificado);

        // =========================
        // 3. PROBAR VEHÍCULO
        // =========================
        Vehiculo vehiculo = new Vehiculo(
                9001,
                "Bicicleta eléctrica",
                15.5,
                25.0,
                "DISPONIBLE"
        );

        boolean vehiculoInsertado = daoVehiculo.insertar(vehiculo);
        System.out.println("Vehículo insertado: " + vehiculoInsertado);

        System.out.println("Vehículos registrados: " + daoVehiculo.listar().size());

        vehiculo.setTipo("Moto eléctrica");
        vehiculo.setCapacidad(40.0);
        vehiculo.setVelocidad(60.0);
        vehiculo.setEstado("EN RUTA");

        boolean vehiculoModificado = daoVehiculo.modificar(vehiculo);
        System.out.println("Vehículo modificado: " + vehiculoModificado);

        // =========================
        // 4. PROBAR PEDIDO
        // =========================
        Pedido pedido = new Pedido(
                9001,
                "Pedido de prueba",
                5.8,
                9001,
                "PENDIENTE"
        );

        boolean pedidoInsertado = daoPedido.insertar(pedido);
        System.out.println("Pedido insertado: " + pedidoInsertado);

        System.out.println("Pedidos registrados: " + daoPedido.listar().size());

        pedido.setDescripcion("Pedido modificado");
        pedido.setDistancia(8.4);
        pedido.setEstado("ASIGNADO");

        boolean pedidoModificado = daoPedido.modificar(pedido);
        System.out.println("Pedido modificado: " + pedidoModificado);

        // =========================
        // 5. PROBAR ENTREGA
        // =========================
        Entrega entrega = new Entrega(
                9001,
                "2026-06-25",
                35.0,
                2.4,
                9001,
                9001,
                "EN PROCESO"
        );

        boolean entregaInsertada = daoEntrega.insertar(entrega);
        System.out.println("Entrega insertada: " + entregaInsertada);

        System.out.println("Entregas registradas: " + daoEntrega.listar().size());

        entrega.setTiempo(28.0);
        entrega.setCo2(1.8);
        entrega.setEstado("FINALIZADA");

        boolean entregaModificada = daoEntrega.modificar(entrega);
        System.out.println("Entrega modificada: " + entregaModificada);

        // =========================
        // 6. ELIMINAR DATOS DE PRUEBA
        // =========================
        boolean entregaEliminada = daoEntrega.eliminar(9001);
        boolean pedidoEliminado = daoPedido.eliminar(9001);
        boolean vehiculoEliminado = daoVehiculo.eliminar(9001);
        boolean clienteEliminado = daoCliente.eliminar(9001);

        if (usuarioLogin != null) {
            boolean usuarioEliminado = daoUsuario.eliminar(usuarioLogin.getCodigo());
            System.out.println("Usuario eliminado: " + usuarioEliminado);
        }

        System.out.println("Entrega eliminada: " + entregaEliminada);
        System.out.println("Pedido eliminado: " + pedidoEliminado);
        System.out.println("Vehículo eliminado: " + vehiculoEliminado);
        System.out.println("Cliente eliminado: " + clienteEliminado);

        System.out.println("===== FIN DE PRUEBA DAO =====");
    }
}