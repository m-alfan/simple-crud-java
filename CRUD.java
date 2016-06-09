import java.sql.*;
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import javax.swing.border.Border;
import javax.swing.table.DefaultTableModel;
import javax.swing.UIManager.LookAndFeelInfo;

public class CRUD extends JFrame {
	//judul
	private JLabel judul;
	private JLabel nama;
	private JLabel npm;
	private JLabel alamat;
	private JLabel agama;

	//field
	private JTextField tnama;
	private JTextField tnpm;
	private JTextArea talamat;
	private JComboBox tagama;
	
	//tabel
	private DefaultTableModel data_tabel;
    private JTable tabelPanel;
    private JScrollPane skrol_tabel;
	
	//button
	private JButton btampil;
	private JButton binput;
	private JButton bubah;
	private JButton bexit;
	private JButton bdelete;

	//database
	private String id_mhs;
	private String query;
    private Koneksidatabase db;
    private PreparedStatement pst;
    private Statement stat;
    private ResultSet rs;

	public CRUD(){
		this.setTitle("CRUD");
		this.setSize(520,530);

		JPanel contentPane = new JPanel(null);
		contentPane.setPreferredSize(new Dimension(520,530));
		contentPane.setBackground(new Color(192,192,192));

		judul = new JLabel();
		judul.setBounds(40,20,155,40);
		judul.setFont(new Font("SansSerif",0,20));
		judul.setText("Data Mahasiswa");
		nama = new JLabel();
		nama.setBounds(40,90,90,35);
		nama.setText("Nama");
		npm = new JLabel();
		npm.setBounds(40,130,90,35);
		npm.setText("NPM");
		alamat = new JLabel();
		alamat.setBounds(40,170,90,35);
		alamat.setText("Alamat");
		agama = new JLabel();
		agama.setBounds(40,280,120,35);
		agama.setText("Agama");		

		tnama = new JTextField();
		tnama.setBounds(100,90,350,35);
		tnpm = new JTextField();
		tnpm.setBounds(100,128,350,35);
		talamat = new JTextArea();
		talamat.setBounds(100,170,350,100);
		tagama = new JComboBox();
		tagama.setBounds(100,280,350,35);
		tagama.addItem("Islam");
		tagama.addItem("Kristen Khatolik");
		tagama.addItem("Kristen Protestan");
		tagama.addItem("Hindu");
		tagama.addItem("Budha");

		String tableTitle[] = {"No","ID","Nama","NPM","Alamat","Agama"};
        data_tabel  = new DefaultTableModel(null,tableTitle);

        tabelPanel = new JTable();
        tabelPanel.setModel(data_tabel);
        tabelPanel.setEnabled(true);
        tabelPanel.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);

        skrol_tabel = new JScrollPane();
        skrol_tabel.getViewport().add(tabelPanel);
        skrol_tabel.setBounds(10,325,500,150);
        getDataTabel();

		btampil = new JButton();
		btampil.setBounds(40,480,90,35);
		btampil.setText("Tampil");
		btampil.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent evt) {
                getDataTabel();
            }
        });

		binput = new JButton();
		binput.setBounds(150,480,90,35);
		binput.setText("Input");
		binput.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent evt) {
                save_data(evt);
                getDataTabel();
            }
        });

		bubah = new JButton();
		bubah.setBounds(260,480,90,35);
		bubah.setText("Ubah");
		bubah.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent evt) {
                update_data(evt);
                getDataTabel();
            }
        });


		bexit = new JButton();
		bexit.setBounds(370,480,90,35);
		bexit.setText("Exit");
		bexit.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent evt) {
                dispose();
            }
        });

		bdelete = new JButton();
		bdelete.setBounds(460,280,35,35);
		bdelete.setText("X");
		bdelete.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent evt) {
                delete_data(evt);
                getDataTabel();
            }
        });

		contentPane.add(judul);
		contentPane.add(nama);
		contentPane.add(npm);
		contentPane.add(alamat);
		contentPane.add(agama);

		contentPane.add(tnama);
		contentPane.add(tnpm);
		contentPane.add(talamat);
		contentPane.add(tagama);

		contentPane.add(skrol_tabel,BorderLayout.CENTER);

		contentPane.add(btampil);
		contentPane.add(binput);
		contentPane.add(bubah);
		contentPane.add(bexit);
		contentPane.add(bdelete);
		
		this.add(contentPane);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);
		this.pack();
		this.setVisible(true);
	}

	private void getDataTabel(){
        try{
            //get data from database
            db   = new Koneksidatabase();
            stat = db.koneksi.createStatement();
            rs   = stat.executeQuery("SELECT * FROM alfan");
            int no = 1;
            data_tabel.setRowCount(0);
            while(rs.next()){
                String t_no 	= Integer.toString(no);
                String t_id 	= rs.getString("id");
                String t_nama   = rs.getString("nama");
                String t_npm  	= rs.getString("npm");
                String t_alamat = rs.getString("alamat");
                String t_agama  = rs.getString("agama");

                String[] t_data = {t_no, t_id, t_nama, t_npm, t_alamat, t_agama};
                data_tabel.addRow(t_data);
                no++;
            }
            rs.close();
            stat.close();
            db.koneksi.close();
        }
        catch(Exception e)
        {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    private void update_data (MouseEvent evt) {
        id_mhs = JOptionPane.showInputDialog(null, "Input ID Mahasiswa ?");
        if(
        	tnama.getText().isEmpty() &&
        	tnpm.getText().isEmpty() &&
        	talamat.getText().isEmpty()
		){
			JOptionPane.showMessageDialog(null,"Please Input All Filed");
        }
        else {
            try{
            	query = "UPDATE alfan SET `nama` = ?, `npm` = ?, `alamat` = ?,`agama` = ? WHERE `id` = ?";
                db   = new Koneksidatabase();
                pst = db.koneksi.prepareStatement(query);
               	
               	pst.setString(1,tnama.getText());
                pst.setString(2,tnpm.getText());
                pst.setString(3,talamat.getText());
                pst.setString(4,tagama.getSelectedItem().toString());
                pst.setString(5,id_mhs);

                pst.execute();
                db.koneksi.close();
                JOptionPane.showMessageDialog(null, "Update Succsessfull");
            }
            catch(Exception e)
            {
                JOptionPane.showMessageDialog(null, e);
            }
        }
    }

    private void save_data (MouseEvent evt) {
        if(
        	tnama.getText().isEmpty() &&
        	tnpm.getText().isEmpty() &&
        	talamat.getText().isEmpty()
		){
			JOptionPane.showMessageDialog(null,"Please Input All Filed");
        }
        else {
            try{
                query = "INSERT INTO alfan (`nama`, `npm`, `alamat`, `agama`) VALUES(?,?,?,?)";
                db   = new Koneksidatabase();
                pst = db.koneksi.prepareStatement(query);
               	
               	pst.setString(1,tnama.getText());
                pst.setString(2,tnpm.getText());
                pst.setString(3,talamat.getText());
                pst.setString(4,tagama.getSelectedItem().toString());

                pst.execute();
                db.koneksi.close();
                JOptionPane.showMessageDialog(null, "Save Succsessfull");
            }
            catch(Exception e)
            {
                JOptionPane.showMessageDialog(null, e);
            }
        }
    }

    private void delete_data (MouseEvent evt) {
        id_mhs = JOptionPane.showInputDialog(null, "Input ID Mahasiswa ?");
	    try{
	        //delete data from database
	        query = "DELETE FROM alfan WHERE id = ?";
	        db   = new Koneksidatabase();
	        pst = db.koneksi.prepareStatement(query);
	        pst.setString(1,id_mhs);
	        pst.execute();
	        db.koneksi.close();
	        JOptionPane.showMessageDialog(null, "Delete Succsessfull");
	    }
	    catch(Exception e)
	    {
	        JOptionPane.showMessageDialog(null, e);
	    }
    }

	public static void main(String[] args){
		System.setProperty("swing.defaultlaf", "com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				new CRUD();
			}
		});
	}
}