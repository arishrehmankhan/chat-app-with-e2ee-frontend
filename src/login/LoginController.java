/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package login;

import HTTPCall.HTTPCallAPI;
import HTTPCall.RetrofitService;
import HTTPCall.User;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * FXML Controller class
 *
 * @author gaurav
 */
public class LoginController implements Initializable {

    @FXML
    private JFXTextField userid;
    @FXML
    private JFXPasswordField password;
    @FXML
    private Text error;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @FXML
    private void onKeepMeSignedClick(javafx.event.ActionEvent event) {
    }

    @FXML
    private void onLoginButtonClick(javafx.event.ActionEvent event) throws IOException {
//        input values
        String userId = this.userid.getText();
        String password = this.password.getText();
        // getting retrofit service object
        RetrofitService retrofit = new RetrofitService();
        HTTPCallAPI service = retrofit.getService();
        // sending URL to server
        User user = new User(userId, password);
        final Call<User> call = service.login(user);
        // making Asynchronous call 
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful()) {
                    User apiResponse = response.body();
                    //API response
                    if (apiResponse.response.equals("Error")) {
                        error.setText(apiResponse.message);
                    } else {
//                        here we will do some specific work.

                    }
                } else {
                    error.setText("Request Error :: " + response.errorBody());
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                error.setText("Network Error :: " + t.getLocalizedMessage());
//                System.out.println("Network Error :: " + t.getLocalizedMessage());
            }
        });
        // getting Synchronous response
        // final Response<User> response = call.execute();
        // System.out.println(response.code());
    }

    @FXML
    private void onDontHaveAccountClick(ActionEvent event) {
        Parent root;
        try {
            root = FXMLLoader.load(getClass().getResource("../register/register.fxml"));
            Stage stage = new Stage();
            stage.setTitle("Register");
            stage.setScene(new Scene(root));
            stage.show();
            //it Hides the current window
            ((Node) (event.getSource())).getScene().getWindow().hide();
        } catch (IOException e) {
        }
    }

    @FXML
    private void onForgotPasswordClick(ActionEvent event) {
        Parent root;
        try {
            root = FXMLLoader.load(getClass().getResource("../forgotpassword/forgotpassword.fxml"));
            Stage stage = new Stage();
            stage.setTitle("Forgot Password");
            stage.setScene(new Scene(root));
            stage.show();
            //it Hides the current window
            ((Node) (event.getSource())).getScene().getWindow().hide();
        } catch (IOException e) {
        }
    }

    @FXML
    private void clearError(MouseEvent event) {
        error.setText("");
    }

}
