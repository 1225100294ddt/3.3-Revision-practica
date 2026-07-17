package utng.gitd232.ddt;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

public class GestionInventarioController {

    @FXML
    private TextField txtCodigo;

    @FXML
    private TextField txtNombre;

    @FXML
    private TextField txtSerie;

    @FXML
    private TextField txtCantidad;

    @FXML
    private TableView<Equipo> tblInventario;

    @FXML
    private TableColumn<Equipo, String> colCodigo;

    @FXML
    private TableColumn<Equipo, String> colNombre;

    @FXML
    private TableColumn<Equipo, String> colSerie;

    @FXML
    private TableColumn<Equipo, Integer> colCantidad;

    @FXML
    private TableColumn<Equipo, String> colEstado;

    private ObservableList<Equipo> listaEquipos = FXCollections.observableArrayList();

    @FXML
    public void initialize() {

        colCodigo.setCellValueFactory(new PropertyValueFactory<>("codigo"));
        colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        colSerie.setCellValueFactory(new PropertyValueFactory<>("serie"));
        colCantidad.setCellValueFactory(new PropertyValueFactory<>("cantidad"));
        colEstado.setCellValueFactory(new PropertyValueFactory<>("estado"));

        tblInventario.setItems(listaEquipos);

        tblInventario.getSelectionModel().selectedItemProperty().addListener((obs, anterior, seleccionado) -> {

            if (seleccionado != null) {

                txtCodigo.setText(seleccionado.getCodigo());
                txtNombre.setText(seleccionado.getNombre());
                txtSerie.setText(seleccionado.getSerie());
                txtCantidad.setText(String.valueOf(seleccionado.getCantidad()));

            }

        });

        listarEquipos();

    }

    @FXML
    private void agregarEquipo() {

        if (!validarCampos()) {
            return;
        }

        String sql = "INSERT INTO inventario(codigo,nombre,serie,cantidad,estado) VALUES(?,?,?,?,?)";

        try (Connection con = ConexionBD.conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, txtCodigo.getText());
            ps.setString(2, txtNombre.getText());
            ps.setString(3, txtSerie.getText());
            ps.setInt(4, Integer.parseInt(txtCantidad.getText()));
            ps.setString(5, "Disponible");

            ps.executeUpdate();

            mostrarMensaje("Equipo agregado correctamente.");

            listarEquipos();

            limpiarCampos();

        } catch (Exception e) {

            e.printStackTrace();

            mostrarMensaje("Error al agregar el equipo.");

        }

    }

    @FXML
    private void modificarEquipo() {

        Equipo equipo = tblInventario.getSelectionModel().getSelectedItem();

        if (equipo == null) {

            mostrarMensaje("Seleccione un equipo para modificar.");

            return;

        }

        if (!validarCampos()) {

            return;

        }

        String sql = "UPDATE inventario SET nombre=?, serie=?, cantidad=? WHERE codigo=?";

        try (Connection con = ConexionBD.conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, txtNombre.getText());
            ps.setString(2, txtSerie.getText());
            ps.setInt(3, Integer.parseInt(txtCantidad.getText()));
            ps.setString(4, txtCodigo.getText());

            ps.executeUpdate();

            mostrarMensaje("Equipo modificado correctamente.");

            listarEquipos();

            limpiarCampos();

        } catch (Exception e) {

            e.printStackTrace();

            mostrarMensaje("Error al modificar el equipo.");

        }

    }
        @FXML
    private void eliminarEquipo() {

        Equipo equipo = tblInventario.getSelectionModel().getSelectedItem();

        if (equipo == null) {

            mostrarMensaje("Seleccione un equipo para eliminar.");

            return;

        }

        String sql = "DELETE FROM inventario WHERE codigo=?";

        try (Connection con = ConexionBD.conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, equipo.getCodigo());

            ps.executeUpdate();

            mostrarMensaje("Equipo eliminado correctamente.");

            listarEquipos();

            limpiarCampos();

        } catch (Exception e) {

            e.printStackTrace();

            mostrarMensaje("Error al eliminar el equipo.");

        }

    }

    @FXML
    private void listarEquipos() {

        listaEquipos.clear();

        String sql = "SELECT * FROM inventario";

        try (Connection con = ConexionBD.conectar();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {

                Equipo equipo = new Equipo(
                        rs.getString("codigo"),
                        rs.getString("nombre"),
                        rs.getString("serie"),
                        rs.getInt("cantidad"),
                        rs.getString("estado")
                );

                listaEquipos.add(equipo);

            }

        } catch (Exception e) {

            e.printStackTrace();

            mostrarMensaje("Error al cargar los equipos.");

        }

    }

    @FXML
    private void limpiarCampos() {

        txtCodigo.clear();
        txtNombre.clear();
        txtSerie.clear();
        txtCantidad.clear();

        tblInventario.getSelectionModel().clearSelection();

    }

    private boolean validarCampos() {

        if (txtCodigo.getText().trim().isEmpty()
                || txtNombre.getText().trim().isEmpty()
                || txtSerie.getText().trim().isEmpty()
                || txtCantidad.getText().trim().isEmpty()) {

            mostrarMensaje("Complete todos los campos.");

            return false;

        }

        try {

            Integer.parseInt(txtCantidad.getText());

        } catch (NumberFormatException e) {

            mostrarMensaje("La cantidad debe ser un número.");

            return false;

        }

        return true;

    }

    private void mostrarMensaje(String mensaje) {

        Alert alerta = new Alert(Alert.AlertType.INFORMATION);

        alerta.setTitle("Inventario");

        alerta.setHeaderText(null);

        alerta.setContentText(mensaje);

        alerta.showAndWait();

    }

}