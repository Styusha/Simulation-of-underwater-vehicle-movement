import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

public class MainForm
{

    private JButton button1;
    private JPanel MainPanel;
    private JTabbedPane tabbedPane1;
    private JPanel Mmenu;
    private JPanel Tobject;
    private JPanel Tm1;
    private JPanel Tm2;
    private JPanel Tm3;
    private JPanel Tintegr;
    private JPanel Tgraph;
    private JPanel MatrObj;
    private JTextField Tb11;
    private JTextField Tb21;
    private JTextField Tb12;
    private JTextField Tb22;
    private JTextField Tb13;
    private JTextField Tb23;
    private JTextField Tkpsi1;
    private JTextField Tpsiz1;
    private JTextField Tt1;
    private JTextField Tkwy1;
    private JTextField Twyz1;
    private JTextField Tbe;
    private JTextField Tpsi;
    private JTextField Twy;
    private JTextField Tv;
    private JTextField sh;
    private JRadioButton bxt;
    private JRadioButton bxp;
    private JRadioButton bxw;
    private JRadioButton bxd;
    private JRadioButton bxk;

    private JRadioButton byt;
    private JRadioButton byp;
    private JRadioButton byw;
    private JRadioButton byd;
    private JRadioButton byk;
    private JTextField Tdz;
    private JTextField Tks;
    private JTextField Tkpsi3;
    private JTextField Tpsiz3;
    private JTextField Tt3;
    private JTextField Tkwy3;
    private JTextField Twyz3;
    private JTextField Tt2;
    private JTextField Tkpsi2;
    private JTextField Tpsiz2;
    private JTextField Tkwy2;
    private JTextField Twyz2;
    private JPanel ForGraph;

    public static void main(String[] args)          //точка входа в программу
    {
        final MainForm MForm = new MainForm();      //создаем экземпляр главной формы
    }

    public MainForm() {

        final JFrame frame = new JFrame("Движение подводного аппарата в горизонтальной плоскости");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //по закрытию формы приложение завершается, используя вызов System.exit().
        frame.setSize(800, 600);                              //размеры выводимой формы
        frame.setLocation(250, 100);
        frame.getContentPane().add(MainPanel);

        MakeMainMenu(frame);
        frame.setVisible(true);
        ForGraph.setVisible(true);

        button1.addActionListener(new ActionListener() {                    //событие по нажатию кнопки start
            @Override
            public void actionPerformed(ActionEvent e)
            {
                DoGraph();
            }
        });
    }


    public int DoGraph() {
        Vector Data_t  = new Vector <Double>();               //организация массивов данных
        Vector Data_Psi = new Vector <Double>();
        Vector Data_Wy = new Vector <Double>();
        Vector Data_Dz = new Vector <Double>();
        Vector Data_Ks = new Vector <Double>();
        Vector<Double> data_x;
        Vector<Double> data_y;

        double b11 = Double.parseDouble(Tb11.getText());       // считывание введённых данных
        double b12 = Double.parseDouble(Tb12.getText());
        double b13 = Double.parseDouble(Tb13.getText());
        double b21 = Double.parseDouble(Tb21.getText());
        double b22 = Double.parseDouble(Tb22.getText());
        double b23 = Double.parseDouble(Tb23.getText());
        double Be = Double.parseDouble(Tbe.getText()) * Math.PI / 180;
        double Wy = Double.parseDouble(Twy.getText()) * Math.PI / 180;
        double Dz = Double.parseDouble(Tdz.getText());
        double Ks = Double.parseDouble(Tks.getText());
        double Psi = Double.parseDouble(Tpsi.getText()) * Math.PI / 180;
        double V = Double.parseDouble(Tv.getText());
        double h = Double.parseDouble(sh.getText());

        int MCount = 1;                                                                 //первый маневр
        double KPsi = Double.parseDouble(Tkpsi1.getText());
        double KWy = Double.parseDouble(Tkwy1.getText());
        double PsiZ = Double.parseDouble(Tpsiz1.getText()) * Math.PI / 180;
        double WyZ = Double.parseDouble(Twyz1.getText()) * Math.PI / 180;
        double tm = Double.parseDouble(Tt1.getText());
        double DVmax = 13*(Math.PI/180);
        double t = 0;
        Data_t.add(t);
        Data_Psi.add(Psi);
        Data_Wy.add(Wy);
        Data_Dz.add(Dz);
        Data_Ks.add(Ks);

        while(true) {                                                                   //второй маневр
            if (t > tm && MCount == 1) {
                tm = t + Double.parseDouble(Tt2.getText());
                KPsi = Double.parseDouble(Tkpsi2.getText());
                KWy = Double.parseDouble(Tkwy2.getText());
                PsiZ = Double.parseDouble(Tpsiz2.getText()) * Math.PI / 180;
                WyZ = Double.parseDouble(Twyz2.getText()) * Math.PI / 180;
                ++MCount;
            }

            if (t > tm && MCount == 2) {                                                //третий маневр
                tm = t + Double.parseDouble(Tt3.getText());
                KPsi = Double.parseDouble(Tkpsi3.getText());
                KWy = Double.parseDouble(Tkwy3.getText());
                PsiZ = Double.parseDouble(Tpsiz3.getText()) * Math.PI/ 180;
                WyZ = Double.parseDouble(Twyz3.getText()) * Math.PI/ 180;
                ++MCount;
            }

            if (t > tm && MCount == 3)    break;

            double Vx = V * Math.cos(Be);
            double Vz = V * Math.sin(Be);
            double DV = KPsi * (Psi - PsiZ) + KWy * (Wy - WyZ);

            if (Math.abs(DV) > DVmax)  DV = DVmax * Math.signum(DV);

            double dBe = b11 * Be + b12 * Wy + b13 * DV;
            double dWy = b21 * Be + b22 * Wy + b23 * DV;
            double dPsi = Wy;
            double dKs = Vx * Math.cos(Psi) + Vz * Math.sin(Psi);
            double dDz = -Vx * Math.sin(Psi) + Vz * Math.cos(Psi);
            Be += dBe * h;
            Wy += dWy * h;
            Psi += dPsi * h;
            Ks += dKs * h;
            Dz += dDz * h;
            t += h;
            Data_t.add(t);
            Data_Psi.add(Psi);
            Data_Wy.add(Wy);
            Data_Dz.add(Dz);
            Data_Ks.add(Ks);
        }

        Graphics2D g2 = (Graphics2D)ForGraph.getGraphics();                      //построение графика
        g2.setColor(Color.BLUE);
        g2.setBackground(ForGraph.getBackground());
        g2.clearRect(2, 2, ForGraph.getWidth() - 4, ForGraph.getHeight() - 4);   //очистка графика
        data_x = null;
        data_y = null;
        if (bxt.isSelected())    data_x = Data_t;                                //определение, на каких осях будет построен график
        if (bxp.isSelected())    data_x = Data_Psi;
        if (bxw.isSelected())    data_x = Data_Wy;
        if (bxd.isSelected())    data_x = Data_Dz;
        if (bxk.isSelected())    data_x = Data_Ks;

        if (byt.isSelected())    data_y = Data_t;
        if (byp.isSelected())    data_y = Data_Psi;
        if (byw.isSelected())    data_y = Data_Wy;
        if (byd.isSelected())    data_y = Data_Dz;
        if (byk.isSelected())    data_y = Data_Ks;

        int Count = data_x.size();
        double regionPart = 0.7;
        int graphWidth = ForGraph.getWidth();
        int graphHeight = ForGraph.getHeight();
        double shiftX = ((double)graphWidth - (double)graphWidth * regionPart) / 2;
        double shiftY = ((double)graphHeight - (double)graphHeight * regionPart) / 2;
        double minX;
        double maxX = minX = data_x.elementAt(0);
        double minY;
        double maxY = minY = data_y.elementAt(0);
        double rx;
        double ry;
        int i;
        for( i = 1; i < Count; ++i) {
            rx = data_x.elementAt(i);
            ry = data_y.elementAt(i);
            if (rx < minX)  minX = rx;
            if (rx > maxX)  maxX = rx;
            if (ry < minY)  minY = ry;
            if (ry > maxY)  maxY = ry;

        }

        double kX = (graphWidth - 1) / (maxX - minX) * regionPart;
        double kY = (graphHeight - 1) / (maxY - minY) * regionPart;
        ry = 0;
        rx = 0;
        int py_prev = 0;
        int px_prev = 0;

        for(i = 0; i < Count; ++i) {
            rx = data_x.elementAt(i);
            ry = data_y.elementAt(i);
            int px = (int)Math.round(shiftX + (rx - minX) * kX);
            int py = graphHeight - (int)Math.round(shiftY + (ry - minY) * kY);
            if (i == 0) {
                px_prev = px;
                py_prev = py;
            }

            g2.drawLine(px_prev, py_prev, px, py);
            px_prev = px;
            py_prev = py;
        }

        g2.dispose();

        return 0; }

        public void MakeMainMenu (JFrame frame) {
            JMenuBar menuBar = new JMenuBar();                  //создание строки меню

            JMenu Menu_File = new JMenu("File");                //заголовок пункта меню ("File")
            JMenuItem MenuItem_Start = new JMenuItem("Start");    //пункт меню
            JMenuItem MenuItem_Exit = new JMenuItem("Exit");    //пункт меню


            Menu_File.add(MenuItem_Start);                       //Добавили пункт меню в меню "File"
            Menu_File.addSeparator();                           //строка-разделитель
            Menu_File.add(MenuItem_Exit);                       //Добавили пункт меню в меню "File"

            JMenu Menu_Help = new JMenu("Help");                //Пункт меню в строке меню ("File")
            JMenuItem MenuItem_About = new JMenuItem("About");  //пункт меню
            Menu_Help.add(MenuItem_About);                      //добавили в меню

            menuBar.add(Menu_File);  //первый заголовок меню в строке меню
            menuBar.add(Menu_Help);  //второй заголовок меню в строке меню

            frame.setJMenuBar(menuBar); //добавили меню в контейнер frame

            //обработчики выбора пунктов меню:
            MenuItem_Exit.addActionListener(new ActionListener() { //добавляем обработчик
                @Override
                public void actionPerformed(ActionEvent e) {
                    System.exit(0); //выход
                }
            });
            MenuItem_About.addActionListener(new ActionListener() { //добавляем обработчик
                @Override
                public void actionPerformed(ActionEvent e) {
                    JOptionPane.showMessageDialog(MenuItem_About, "Программа моделирует движение подводного аппарата \n" +
                            "на трёх учасках (три манёвра разной продолжительности).\n" +
                            "Пользователю следует ввести все необходимые коэффициенты\n" +
                            "и нажать кнопку ''start'' для построения графика");
                }
            });

            MenuItem_Start.addActionListener(new ActionListener() { //добавляем обработчик
                @Override
                public void actionPerformed(ActionEvent e) {
                    DoGraph();
                }
            });

        }}
