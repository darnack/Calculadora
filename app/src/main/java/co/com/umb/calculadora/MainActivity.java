package co.com.umb.calculadora;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private Button btnPlus, btnMinus, btnMulti, btnDiv;
    private Button btnBack, btnCE, btnUp, btnDown, btnEqual, btnComma;
    private Button btnZero, btnOne, btnTwo, btnThree, btnFour, btnFive, btnSix, btnSeven, btnEight, btnNine;
    private TextView txtExpression, txtResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Obtiene las referencias de los componentes UI para su manipulación
        ReferenceUIComponents();
        // Agrega el comportamiento a los componentes
        InitializeEvents();
    }

    private void ReferenceUIComponents() {
        btnPlus = (Button)findViewById(R.id.btnPlus);
        btnMinus = (Button)findViewById(R.id.btnMinus);
        btnMulti = (Button)findViewById(R.id.btnMulti);
        btnPlus = (Button)findViewById(R.id.btnPlus);
        btnDiv = (Button)findViewById(R.id.btnDiv);
        btnBack = (Button)findViewById(R.id.btnBack);
        btnCE = (Button)findViewById(R.id.btnCE);
        btnUp = (Button)findViewById(R.id.btnUp);
        btnDown = (Button)findViewById(R.id.btnDown);
        btnEqual = (Button)findViewById(R.id.btnEqual);
        btnComma = (Button)findViewById(R.id.btnComma);
        btnZero = (Button)findViewById(R.id.btnZero);
        btnOne = (Button)findViewById(R.id.btnOne);
        btnTwo = (Button)findViewById(R.id.btnTwo);
        btnThree = (Button)findViewById(R.id.btnThree);
        btnFour = (Button)findViewById(R.id.btnFour);
        btnFive = (Button)findViewById(R.id.btnFive);
        btnSix = (Button)findViewById(R.id.btnSix);
        btnSeven = (Button)findViewById(R.id.btnSeven);
        btnEight = (Button)findViewById(R.id.btnEight);
        btnNine = (Button)findViewById(R.id.btnNine);
        txtExpression = (TextView)findViewById(R.id.txtExpression);
        txtResult = (TextView)findViewById(R.id.txtResult);
    }

    private void InitializeEvents() {

        View.OnClickListener writeDigitEvent = new View.OnClickListener() {
            public void onClick(View element) {
                String expression = (String) txtExpression.getText();
                if(expression.length() < 21) {
                    String symbol = (String) ((Button) element).getText();
                    txtExpression.setText(expression + symbol);
                }
            }
        };

        View.OnClickListener writeSymbolEvent = new View.OnClickListener() {
            public void onClick(View element) {
                String expression = (String) txtExpression.getText();
                if( expression.length() > 0 && expression.length() < 20 ) {
                    CharSequence lastChar = expression.subSequence(expression.length()-1, expression.length());
                    if(android.text.TextUtils.isDigitsOnly(lastChar)) {
                        String symbol = (String) ((Button) element).getText();
                        txtExpression.setText(expression + symbol);
                    }
                }
            }
        };

        View.OnClickListener backEvent = new View.OnClickListener() {
            public void onClick(View element) {
                String expression = (String) txtExpression.getText();
                if(expression.length() > 0) {
                    expression = expression.substring(0, expression.length() - 1);
                    txtExpression.setText(expression);
                }
            }
        };

        View.OnClickListener clearEvent = new View.OnClickListener() {
            public void onClick(View element) {
                txtExpression.setText("");
                txtResult.setText("0");
            }
        };

        btnZero.setOnClickListener(writeDigitEvent);
        btnOne.setOnClickListener(writeDigitEvent);
        btnTwo.setOnClickListener(writeDigitEvent);
        btnThree.setOnClickListener(writeDigitEvent);
        btnFour.setOnClickListener(writeDigitEvent);
        btnFive.setOnClickListener(writeDigitEvent);
        btnSix.setOnClickListener(writeDigitEvent);
        btnSeven.setOnClickListener(writeDigitEvent);
        btnEight.setOnClickListener(writeDigitEvent);
        btnNine.setOnClickListener(writeDigitEvent);
        btnPlus.setOnClickListener(writeSymbolEvent);
        btnMinus.setOnClickListener(writeSymbolEvent);
        btnMulti.setOnClickListener(writeSymbolEvent);
        btnDiv.setOnClickListener(writeSymbolEvent);
        btnComma.setOnClickListener(writeSymbolEvent);
        btnBack.setOnClickListener(backEvent);
        btnCE.setOnClickListener(clearEvent);
    }
}
