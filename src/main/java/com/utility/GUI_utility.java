package com.utility;

import com.GUI.myApp;
import javafx.collections.ObservableList;
import javafx.geometry.VPos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public final class GUI_utility {
    private GUI_utility() {}


    public static Group UML (Color color, double scale, String string)
    {
        Group root = new Group();

        Rectangle myRectangle = new Rectangle();
        myRectangle.setX(myApp.length*(1-scale)*0.5);myRectangle.setY(myApp.length*(1-scale)*0.5);
        myRectangle.setWidth(myApp.length*scale);myRectangle.setHeight(myApp.length*scale);
        myRectangle.setFill(Color.rgb(254,187,100));
        myRectangle.setStrokeWidth(5);
        myRectangle.setArcHeight(50);myRectangle.setArcWidth(50);
        myRectangle.setStroke(color);
        root.getChildren().add(myRectangle);


        Line myLine = new Line();
        myLine.setStartX(myApp.length* (0.5-0.5*scale));myLine.setStartY(myApp.length* ((1-scale)*0.5 + scale*0.2));
        myLine.setEndX(myApp.length*(0.5+0.5*scale)); myLine.setEndY(myApp.length* ((1-scale)*0.5 + scale*0.2));
        myLine.setStrokeWidth(5);
        myLine.setStroke(color);
        root.getChildren().add(myLine);

        Text Header = new Text(string);
        Header.setTextOrigin(VPos.CENTER);
        Header.setY(((1-myApp.scale)*0.5 + myApp.scale*0.1) * myApp.length);Header.setX(myApp.length* ((1-myApp.scale+0.1)*0.5));
        Header.setFont(Font.font("Consolas", 0.0625*myApp.length*myApp.scale*1.25*0.6));
        Header.setFill(Color.BLACK);
        root.getChildren().add(Header);

        return root;

    }

    public static Button set_button_onGrid(final int row, final int column, GridPane gridPane)
    {
        Button btnn = new Button();
        Node myNode = getNodeByRowColumnIndex(row, column, gridPane);
        btnn.setGraphic(myNode);
        btnn.setLayoutX(gridPane.getLayoutX()-7);
        btnn.setLayoutY(gridPane.getLayoutY() + row * 50-4);

        return btnn;
    }

    public static Node getNodeByRowColumnIndex (final int row, final int column, GridPane gridPane) {
        Node result = null;
        ObservableList<Node> childrens = gridPane.getChildren();

        for (Node node : childrens) {
            if(gridPane.getRowIndex(node) == row && gridPane.getColumnIndex(node) == column) {

                result = node;
                break;
            }
        }

        return result;
    }



}
