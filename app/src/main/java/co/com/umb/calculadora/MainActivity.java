package co.com.umb.calculadora;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import co.com.umb.calculadora.entities.Record;

public class MainActivity extends AppCompatActivity {

    private Button btnPlus, btnMinus, btnMulti, btnDiv;
    private Button btnBack, btnCE, btnUp, btnDown, btnEqual, btnComma;
    private Button btnZero, btnOne, btnTwo, btnThree, btnFour, btnFive, btnSix, btnSeven, btnEight, btnNine;
    private TextView txtExpression, txtResult;

    private List<Record> record = new ArrayList<Record>();
    private int indexRecord=0;
    private boolean reWrite=false;

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

        // Este evento gestiona la escritura de digitos en la pantalla
        View.OnClickListener writeDigitEvent = new View.OnClickListener() {
            public void onClick(View element) {
                if(reWrite){ clear();}
                String expression = (String) txtExpression.getText();
                // Se permite la escritura de hasta 20 caracteres en pantalla
                if(expression.length() < 21) {
                    String digit = (String) ((Button) element).getText();
                    txtExpression.setText(expression + digit);
                    reWrite = false;
                }
            }
        };

        // Este evento gestiona la escritura de símbolos en la pantalla
        View.OnClickListener writeSymbolEvent = new View.OnClickListener() {
            public void onClick(View element) {

                String expression = (String) txtExpression.getText();
                // No se permite ingreso de símbolos en la primera ni última posición
                if( expression.length() > 0 && expression.length() < 20 ) {
                    CharSequence lastChar = expression.subSequence(expression.length()-1, expression.length());
                    // No permite el ingreso de dos símbolos consecutivos
                    if(android.text.TextUtils.isDigitsOnly(lastChar)) {
                        String symbol = (String) ((Button) element).getText();
                        txtExpression.setText(expression + symbol);
                        reWrite = false;
                    }
                }
            }
        };

        // Este evento elimina un caracter a la vez
        View.OnClickListener backEvent = new View.OnClickListener() {
            public void onClick(View element) {
                String expression = (String) txtExpression.getText();
                if(expression.length() > 0) {
                    expression = expression.substring(0, expression.length() - 1);
                    txtExpression.setText(expression);
                    reWrite=false;
                }
            }
        };

        // Este evento limpia la pantalla
        View.OnClickListener clearEvent = new View.OnClickListener() {
            public void onClick(View element) {
               clear();
            }
        };

        // Este evento carga la siguiente operación del historial
        View.OnClickListener upEvent = new View.OnClickListener() {
            public void onClick(View element) {
                if( (indexRecord) > 0) {
                    indexRecord -= 1;
                    txtExpression.setText(record.get(indexRecord).Expression);
                    txtResult.setText(record.get(indexRecord).Result);
                    reWrite = true;
                }
            }
        };

        // Este evento carga la última operación del historial
        View.OnClickListener downEvent = new View.OnClickListener() {
            public void onClick(View element) {
                if( (indexRecord+1) < record.size()) {
                    indexRecord += 1;
                    txtExpression.setText(record.get(indexRecord).Expression);
                    txtResult.setText(record.get(indexRecord).Result);
                    reWrite = true;
                }
            }
        };

        // Este evento procesa la fórmula y calcula el resultado
        View.OnClickListener EqualEvent = new View.OnClickListener() {
            public void onClick(View element) {

                String expression = txtExpression.getText().toString();
                CharSequence lastChar = expression.subSequence(expression.length()-1, expression.length());
                // No permite el cálculo de expresiones terminadas con símbolo
                if(android.text.TextUtils.isDigitsOnly(lastChar)) {
                    // Realiza el cálculo de la expresión
                    double result = compute(expression);
                    txtResult.setText(String.valueOf(result));

                    // Guardar la operación realizada en el historial (opcional)
                    Record rec = new Record();
                    rec.Expression = expression;
                    rec.Result = String.valueOf(result);
                    record.add(rec);
                    indexRecord = record.size()-1;
                    reWrite = true;
                }
            }
        };

        //*** Se agrega la escucha del evento correspondiente a cada botón
        // Botones de dígitos
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
        // Botones de símbolos
        btnPlus.setOnClickListener(writeSymbolEvent);
        btnMinus.setOnClickListener(writeSymbolEvent);
        btnMulti.setOnClickListener(writeSymbolEvent);
        btnDiv.setOnClickListener(writeSymbolEvent);
        btnComma.setOnClickListener(writeSymbolEvent);
        // Botones de funciones
        btnBack.setOnClickListener(backEvent);
        btnCE.setOnClickListener(clearEvent);
        btnUp.setOnClickListener(upEvent);
        btnDown.setOnClickListener(downEvent);
        btnEqual.setOnClickListener(EqualEvent);
    }

    private void clear()
    {
        txtExpression.setText("");
        txtResult.setText("0");
    }
    //Fuente: https://codereview.stackexchange.com/questions/84763/evaluating-an-expression-with-integers-and-as-well-as
    private double compute(String equation) {
        double result = 0.0;
        String noMinus = equation.replace("-", "+-");
        String[] byPluses = noMinus.split("\\+");

        for (String multipl : byPluses) {
            String[] byMultipl = multipl.split("\\*");
            double multiplResult = 1.0;
            for (String operand : byMultipl) {
                if (operand.contains("/")) {
                    String[] division = operand.split("\\/");
                    double divident = Double.parseDouble(division[0]);
                    for (int i = 1; i < division.length; i++) {
                        divident /= Double.parseDouble(division[i]);
                    }
                    multiplResult *= divident;
                } else {
                    multiplResult *= Double.parseDouble(operand);
                }
            }
            result += multiplResult;
        }

        return result;
    }
}
