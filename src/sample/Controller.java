package sample;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class Controller {
    @FXML
    private TextField heightField;
    @FXML
    private TextField weightField;
    @FXML
    private Button calculateButton;
    @FXML
    private Label BMIResultLabel;
    @FXML
    private Label BMICategoryLabel;
    @FXML
    private Label BMIRecommendationLabel;

    @FXML
    public void initialize(){
        calculateButton.setDisable(true);
    }
    @FXML
    public void onCalculateButtonClicked(){
        double bmi = calculateBMI();
        String bmiCategory = categorizeBMI(bmi);
        String recommendation = makeRecommendation(bmi);
        BMIResultLabel.setText("Your Body Mass Index is " + String.format("%.2f", bmi) + ".");
        BMICategoryLabel.setText("You are " + bmiCategory);
        BMIRecommendationLabel.setText(recommendation);
    }
    @FXML
    public void handleKeyReleased(){
        String weight = weightField.getText();
        String height = heightField.getText();

        boolean disableCalculateButton = weight.equals("Weight (KG)") || height.equals("Height (CM)") ||
                weight.trim().isEmpty() || height.trim().isEmpty();
        calculateButton.setDisable(disableCalculateButton);
    }
    @FXML
    public void handleHeightFieldSelected(){
        heightField.clear();
    }
    @FXML
    public void handleWeightFieldSelected(){
        weightField.clear();
    }
    @FXML
    public void onResetButtonClicked(){
        heightField.setText("Height (CM)");
        weightField.setText("Weight (KG)");

        BMIResultLabel.setText("");
        BMICategoryLabel.setText("");
        BMIRecommendationLabel.setText("");
    }

    private double calculateBMI(){
        double height = Double.parseDouble(heightField.getText())/100;
        double weight = Double.parseDouble(weightField.getText());
        if (height<=0 || height>=3 || weight <=0 || weight>=500){
            return -1;
        }
        return weight/(height*height);
    }

    private String categorizeBMI(double BMI){
        String BMICategory;
        if (BMI>=30){
            BMICategory = "obese.";
        } else if (BMI>=25) {
            BMICategory = "overweight.";
        } else if (BMI>=18.5){
            BMICategory = "healthy.";
        } else
            BMICategory = "underweight.";
        return BMICategory;
    }

    private String makeRecommendation(double bmi){
        double height = Double.parseDouble(heightField.getText())/100;
        double weight = Double.parseDouble(weightField.getText());
        double requiredWeight;
        String recommendation;

        if (bmi < 18.5){
            requiredWeight = (18.5 * height * height) - weight;
            recommendation = "You need to gain at least " + String.format("%.2f", requiredWeight);
        } else if (bmi >= 25) {
            requiredWeight = weight - (25 * height * height);
            recommendation = "You need to lose at least " + String.format("%.2f", requiredWeight) + "KG";
        } else {
            double maxWeight, minWeight;
            minWeight = 18.5 * height * height;
            maxWeight = 25 * height * height;
            recommendation = "Keep your weight between " + String.format("%.1f", minWeight)
                                + "KG and " + String.format("%.1f",maxWeight) + "KG.";
        }

        return recommendation;
    }
}
