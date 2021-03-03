package StartView;

import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;

import FileManeger.JDBCConnection;
import Information.ParkCarInfo;

@SuppressWarnings("serial")
public class out {
	protected Connection conn = null;
	protected PreparedStatement pstmt = null;
	String Code = null;
	String CodeSearch = null;
	String Time = null;
	boolean isfound = false;
	int inputCode = 0;
	
	ParkingStartView2 frame;
	JPanel frameContentPane = new JPanel(null);

	JLabel codeLabel = new JLabel();
	ImageIcon codeImgIcon;
	Image codeImg;
	JButton confirmButton = new JButton();
	JButton backButton = new JButton();
	ImageIcon confirmIcon;
	ImageIcon backIcon;
	Image confirmImg;
	Image backImg;
	JTextField input = new JTextField();
	Border border = new LineBorder(Color.GRAY, 2, true);
	JLabel info = new JLabel();
	JTextField in = new JTextField();
	JTextField inRes = new JTextField();
	JTextField out = new JTextField();
	JTextField outRes = new JTextField();
	JTextField fee = new JTextField();
	JTextField feeRes = new JTextField();
	JButton confirm2Button = new JButton();
	ImageIcon confirm2Icon;
	Image confirm2Img;
	Font font = new Font("굴림", Font.BOLD, 28);
	
	
	public out(ParkingStartView2 frame) {
		this.frame = frame;
		this.frame.setContentPane(frameContentPane);
		
		frameContentPane.setLayout(null);
		frameContentPane.setBackground(Color.white);
		
		frameContentPane.add(codeLabel); 
		codeLabel.setBounds(350, -100, 1000, 400);
		codeImgIcon = new ImageIcon("codeLabel.png");
		codeImg = codeImgIcon.getImage();
		codeImg = codeImg.getScaledInstance(500,200, java.awt.Image.SCALE_SMOOTH);
		codeImgIcon = new ImageIcon(codeImg);
		codeLabel.setIcon(codeImgIcon);
		
		frameContentPane.add(confirmButton);
		confirmButton.setBounds(755, 220, 95, 70);

		confirmIcon = new ImageIcon("확인.png");
		confirmImg = confirmIcon.getImage();
		confirmImg = confirmImg.getScaledInstance(95, 70, java.awt.Image.SCALE_SMOOTH);
		confirmIcon = new ImageIcon(confirmImg);
		confirmButton.setIcon(confirmIcon);
		
		frameContentPane.add(backButton);
		backButton.setBounds(350, 220, 95, 70);

		backIcon = new ImageIcon("back.png");
		backImg = backIcon.getImage();
		backImg = backImg.getScaledInstance(95, 70, java.awt.Image.SCALE_SMOOTH);
		backIcon = new ImageIcon(backImg);
		backButton.setIcon(backIcon);
		
		frameContentPane.add(input);
		input.setBounds(450, 220, 300, 70);
		input.setBorder(border);
		input.setFont(font);
		input.setLayout(null);

		info();
		
		confirmButton.addActionListener(infoAction);
		backButton.addActionListener(backAction);
		
//		setVisible(true);
	}
	
	public void info() {

		frameContentPane.add(info);
		info.setBounds(350, 310, 500, 300);
		info.setBorder(border);
		
		info.add(in);
		in.setBorder(null);
		in.setBounds(100, 20, 150, 50);
		in.setForeground(Color.gray);
		in.setFont(font);
		in.setText("입차시간 : ");
		in.setBackground(Color.white);
		in.setEditable(false);
		
		info.add(inRes);
		inRes.setBorder(null);
		inRes.setBounds(250, 20, 150, 50);
		inRes.setForeground(Color.gray);
		inRes.setFont(font);
		inRes.setText("in result");
		inRes.setBackground(Color.white);
		inRes.setEditable(false);
		
		info.add(out);
		out.setBorder(null);
		out.setBounds(100, 80, 150, 50);
		out.setForeground(Color.gray);
		out.setFont(font);
		out.setText("출차시간 : ");
		out.setBackground(Color.white);
		out.setEditable(false);
		
		info.add(outRes);
		outRes.setBorder(null);
		outRes.setBounds(250, 80, 150, 50);
		outRes.setForeground(Color.gray);
		outRes.setFont(font);
		outRes.setText("out Result");
		outRes.setBackground(Color.white);
		outRes.setEditable(false);
		
		
		
		info.add(fee);
		fee.setBorder(null);
		fee.setBounds(100, 140, 90, 50);
		fee.setForeground(Color.gray);
		fee.setFont(font);
		fee.setText("요금 : ");
		fee.setBackground(Color.white);
		fee.setEditable(false);
		
		info.add(feeRes);
		feeRes.setBorder(null);
		feeRes.setBounds(200, 140, 200, 50);
		feeRes.setForeground(Color.gray);
		feeRes.setFont(font);
		feeRes.setText("fee Result");
		feeRes.setBackground(Color.white);
		feeRes.setEditable(false);
		
		
		info.add(confirm2Button);
		confirm2Button.setBounds(190, 200, 120, 75);
		confirm2Icon = new ImageIcon("확인.png");
		confirm2Img = confirm2Icon.getImage();
		confirm2Img = confirm2Img.getScaledInstance(120, 75, java.awt.Image.SCALE_SMOOTH);
		confirm2Icon = new ImageIcon(confirm2Img);
		confirm2Button.setIcon(confirm2Icon);
	
		confirm2Button.addActionListener(move);
		
		info.setVisible(false);
	}
	
	private void search(int CodeSearch) {
		JDBCConnection jdbc = JDBCConnection.getInstance();
		Connection conn = jdbc.getConnection();
		PreparedStatement pstmt = null;
		
		ResultSet result = null;
		isfound = false;
		
		// 입력받은 parkingcode에 있는 입차시간 불러오기
		try {
			String sql = "select CARINTIME from PARKING WHERE PARKINGCODE = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, CodeSearch);
			result = pstmt.executeQuery();
			while (result.next()) {
				String cartime = result.getNString("CARINTIME");
				isfound = true;
				moneyOp(cartime);
				}
		} catch (SQLException e1) {
			e1.printStackTrace();
		} finally {
			if(isfound == false) {
				input.setText("주차번호 미발견");
				// DB에 존재하지 않을 경우 input 텍스트필드에 출력
			}
		}

	}
	
	private void moneyOp(String cartime) {
		Date date = new Date();
		int hoursNow = date.getHours();
		int minutesNow = date.getMinutes();
		int secondsNow = date.getSeconds(); 
		String out = hoursNow+":"+minutesNow+":"+secondsNow; // 현재 시간
		
        int moneyHour = 0;
        int moneyMinute = 0;
        int moneySeconds = 0;
        
		 String HourString = cartime.charAt(0)+""+cartime.charAt(1);
		 int parkingHour = Integer.parseInt(HourString);
		 String MinuteString = cartime.charAt(2)+""+cartime.charAt(3);
		 int parkingMinute = Integer.parseInt(MinuteString);
		 String SecondsString = cartime.charAt(4)+""+cartime.charAt(5);
		 int parkingSeconds = Integer.parseInt(SecondsString);
		
		// 현재시간 - 입차시간
        if((hoursNow - parkingHour)>=0){
            moneyHour = hoursNow - parkingHour;
        } else{
        }

        if((minutesNow - parkingMinute)>=0){
            moneyMinute = minutesNow - parkingMinute;
        } else{
            moneyHour--;
            moneyMinute = (minutesNow + 60) - parkingMinute;
        }

        if((secondsNow - parkingSeconds)>=0){
            moneySeconds = secondsNow - parkingSeconds;
        } else{
            moneyMinute--;
            moneySeconds = (secondsNow + 60) - parkingSeconds;
        }

        int totalSeconds = (moneyHour*3600) + (moneyMinute*60) + moneySeconds ; // 총 시간(초 단위)
        
        outRes.setText(out);
        inRes.setText(HourString+":"+MinuteString+":"+SecondsString); // 입차시간 출력
        feeRes.setText(totalSeconds*100+"원"); // 초당 100원 계산
	}
	
	
	private void delete(int CodeSearch) {
		JDBCConnection jdbc = JDBCConnection.getInstance();
		Connection conn = jdbc.getConnection();
		PreparedStatement pstmt = null;
		
		try {
			String sql = "DELETE FROM PARKING WHERE PARKINGCODE = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, CodeSearch);
            if(pstmt.executeUpdate()==1){
                System.out.println("회원정보를 성공적으로 삭제했습니다.");
            }else{
                System.out.println("회원정보 삭제에 실패했습니다.");
            }
		} catch (SQLException e1) {
			e1.printStackTrace();
		} finally {
			try {
				if (pstmt != null && !pstmt.isClosed()) {
					pstmt.close();
					System.out.println("pstmt 자원반납");
				} else {
					System.out.println("이미 반납됬거나 예외가 발생된 상태");
				}
			} catch (SQLException e1) {
			}
			jdbc.close();
		}
	}
	
	private ActionListener infoAction = new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			inputCode = 0;
			
			try {
				inputCode = Integer.parseInt(input.getText());
			} catch (Exception e2) {
				input.setText("숫자를 입력해주십시오.");
			}
			search(inputCode);
			
			if(info.isVisible()==false) {
				if(input.getText().equals("숫자를 입력해주십시오.")||
						input.getText().equals("주차번호 미발견")) {
				} else {
					info.setVisible(true);
				}
			} else {
				if(input.getText().equals("숫자를 입력해주세요.")||
						input.getText().equals("주차번호 미발견")) {
					info.setVisible(false);
				}
			}
		}
	};
	
	private ActionListener backAction = new ActionListener() {
		
		@Override
		public void actionPerformed(ActionEvent e) {
			frame.dispose();
			new ParkingStartView2();
		}
	};
	
	private ActionListener move = new ActionListener() {
		
		@Override
		public void actionPerformed(ActionEvent e) {
			delete(inputCode);
			frame.dispose();
			new ParkingStartView2();
		}
	};
}