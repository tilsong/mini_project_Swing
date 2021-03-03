package MainFunctionView;

import StartProgram.*;
import StartView.*;
import MainFunctionView.*;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.ImageProducer;
import java.text.SimpleDateFormat;
import java.util.*;

import javax.swing.*;
import javax.swing.event.*;

import FileManeger.FileSystem;
import Information.ParkCarInfo;

public class ParkingCarIn extends JOptionPane implements ActionListener {
	ParkingStartView2 frame; // ParkingStartView의 프레임을 받아오기 위한 객체 변수 선언

	FileSystem f = new FileSystem();
	JDialog parkInputDialog; // 각 버튼 클릭시 생성될 '주차입고 다이어로그'
	JPanel dialogFullPanel = new JPanel(null); // 각 패널을 배치할 fullPanel 컨테이너
	JLabel titleLabel = new JLabel(); // parkInputDialog의 제목을 출력할 Label

	JLabel carNumLabel = new JLabel("차량번호 : "); // 차량번호를 출력할 Label
	JTextField inputNumText = new JTextField(null, 9); // 최대 9칸까지!
	JTextField inputphoneNumText = new JTextField(null, 12);
	JButton parkOkButton = new JButton("주차등록"); // '주차등록'버튼
	JButton cancelButton = new JButton("취소"); // '취소'버튼
	JButton parkCheckButton = new JButton("확인");
	JLabel phoneNumLabel = new JLabel("전화번호 : "); // 차량번호를 출력할 Label
	// ParkingMainView에서 받아와야 할 변수들
	int parkNum; // 주차한 공간의 인덱스 번호
	JButton parkButton; // 클릭한 주차공간의 버튼 정보
	// '확인'버튼

	public ParkingCarIn(ParkingStartView2 frame) { // ParkingSystem에서 버튼(주차공간) 클릭 이벤트 발생 시 이쪽으로 넘어오게 된다.
													// //ParkingCarIn클래스의 객체 생성과 동시에 다이어로그를 구성해놓는다.
		this.frame = frame; // ParkingStartView클래스에서 생성된 JFrame을 가져온다.
		// 각 버튼의 이벤트 핸들러는 한개씩만 존재해야 하므로 생성자에 넣어준다! , 이렇게 하는게 좋다!
		parkOkButton.addActionListener(new parkOkListener());
		cancelButton.addActionListener(new cancelListener());
		parkCheckButton.addActionListener(new parkCheckListener());
	} // ParkingCarIn 생성자 End

	@Override
	public void actionPerformed(ActionEvent e) { // 각 '주차공간'버튼 클릭 시 발생한 이벤트를 처리하는 핸들러
		// TODO Auto-generated method stub

		inputNumText.setText("");// 텍스트 필드는 다이어로그 호출 시 항상 초기화 상태로!
		inputphoneNumText.setText("");
		makeRegisterDialog(); // '주차등록' 다이어로그 호출

		// 나중에 '출차완료'클릭 시 주차했을 당시 버튼의 인덱스번호와 그 버튼이 무엇이었는지 알아야한다. (그래야 버튼을 다시 활성화하고 그 공간에
		// 주차했던 내용을 삭제한다.)
		parkNum = Integer.parseInt(e.getActionCommand()); // 주차공간 버튼 클릭시 해당 주차공간의 인덱스 번호를 가져옴
		parkButton = (JButton) e.getSource(); // 이벤트가 발생한 버튼을 가져옴

		titleLabel.setText("" + parkNum + "번 주차공간입니다.");// titleLabel 내용변경
		titleLabel.setFont(new Font("고딕", 35, 20));
		parkInputDialog.setVisible(true); // 다이어로그 구성이 완료되었으니 화면에 출력!!
	} // '주차공간' 이벤트 핸들러(actionPerformed)' End

	public void makeRegisterDialog() {// 등록 다이어로그 창 구성
		parkInputDialog = new JDialog(frame, "차량 정보 입력", true); // ParkingStartView객체의 Frame에 다이어로그를 띄운다
		parkInputDialog.setBounds(frame.getWidth() / 2, frame.getHeight() / 2, 300, 200); // 차량 입력 다이어로그를 Frame의 가운데에
																							// 나타내기
		parkInputDialog.add(dialogFullPanel); // 다이어로그 안에 들어갈 내용을 구성한 fullPanel을 다이어로그에 add!

		dialogFullPanel.add(titleLabel); // 다이어로그 상단에 제목출력 Label
		titleLabel.setBounds(55, 10, 250, 20); // -번 주차 공간입니다.

		dialogFullPanel.add(carNumLabel); // 차 번호 출력 Label
		carNumLabel.setBounds(50, 36, 80, 30);

		dialogFullPanel.add(inputNumText); // 차 번호 입력 텍스트필드
		inputNumText.setBounds(120, 35, 120, 30);
	
		dialogFullPanel.add(phoneNumLabel); // 차 번호 출력 Label
		phoneNumLabel.setBounds(50, 70, 80, 30);
		
		dialogFullPanel.add(inputphoneNumText); // 전화번호 입력 텍스트필드
		inputphoneNumText.setBounds(120, 70, 120, 30);
		
		dialogFullPanel.add(parkOkButton); // '주차완료'버튼
		parkOkButton.setBounds(40, 110, 100, 30);

		dialogFullPanel.add(cancelButton); // '주차취소'버튼
		cancelButton.setBounds(150, 110, 100, 30);

	} // makeRegisterDialog() End

	class parkOkListener implements ActionListener {// '주차등록' 클릭 시 이벤트를 처리 할 이벤트 리스너
		@Override
		public void actionPerformed(ActionEvent e) {
			storeCarInfoIntoList(); // 위의 모든 오류들을 바로잡고 입력된 숫자면 주차공간에 등록하게 한다!
			// 주차코드, 주차시간, 주차 공간, 주차 번호를 알려주는 다이얼로그 띄우기 // 그리고 할 수 있다면 이러한 내용을 문자나, 이메일이나,
			// 카톡으로 전송할 수 있도록 하기
			codeCheckDialog();
			codeCheckDialog.setVisible(true);
		}
	}// parkOkListener 클래스 End

	JPanel dialogFullPanel2 = new JPanel(null);
	JLabel codeTitleLabel = new JLabel();
	JDialog codeCheckDialog;

	public void codeCheckDialog() {
		codeCheckDialog = new JDialog(frame, "주차 코드 확인", true); // ParkingStartView객체의 Frame에 다이어로그를 띄운다
		codeCheckDialog.setBounds(frame.getWidth() / 2, frame.getHeight() / 2, 300, 230); // 차량 입력 다이어로그를 Frame의 가운데에
																							// 나타내기
		codeCheckDialog.add(dialogFullPanel2); // 다이어로그 안에 들어갈 내용을 구성한 fullPanel을 다이어로그에 add!
		
		JLabel carNumLabel2 = new JLabel("차량 번호: " + inputNumText.getText());
		dialogFullPanel2.add(carNumLabel2); // 주차 공간 출력 Label
		carNumLabel2.setBounds(30, 0, 200, 50);
		
		JLabel parkCheckPlace = new JLabel("주차 공간: " + parkNum);
		dialogFullPanel2.add(parkCheckPlace); // 차량 번호 출력 Label
		parkCheckPlace.setBounds(30, 25, 200, 50);
		
		Calendar presentTime = Calendar.getInstance(); // 차량 주차 등록을 클릭한 시간 얻어오기
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH " + "시" + "mm" + "분" + "ss" + "초"); // 시간을 저장할 틀
		String time = format.format(presentTime.getTime()); // '주차내역'테이블에 출력하기 위해 변환해서 저장
		
		SimpleDateFormat formatDB = new SimpleDateFormat("HHmmss"); // 시간을 저장할 틀
		String timeDB = formatDB.format(presentTime.getTime()); // 'MINI' DB 테이블에 저장하기 위해 변환해서 저장
		
		JLabel parkInTime = new JLabel("입차 시간:  " + time);
		dialogFullPanel2.add(parkInTime); // 입차 시간 출력 Label
		parkInTime.setBounds(30, 45, 240, 60);

		int parkingCode = (int) (10000 * Math.random());
		JLabel parkCheckCode = new JLabel("주차 코드: " + parkingCode);
		dialogFullPanel2.add(parkCheckCode); // 주차코드 출력 Label
		parkCheckCode.setBounds(30, 75, 150, 50);

		
		JLabel info = new JLabel("휴대전화로 주차코드가 전송되었습니다.");
		dialogFullPanel2.add(info); // 입차 시간 출력 Label
		info.setBounds(30, 95 , 240, 60);
		
		dialogFullPanel2.add(parkCheckButton); // '확인'버튼
		parkCheckButton.setBounds(90, 142, 100, 30);
		f.connection(new ParkCarInfo(parkNum, inputNumText.getText(), timeDB, parkingCode));
		
		SendTextMessage stm = new SendTextMessage();
		stm.messageGo(inputNumText.getText(), inputphoneNumText.getText(), parkingCode);
	}

	public void storeCarInfoIntoList() {
		parkButton.setText("주차중"); // 주차 등록이 완료되면 그 주차공간은 '주차중'이라는 표시를 하게함. ParkingMainView에서 주차공간 클릭 시 이 클래스의 이벤트
									// 리스너로 클릭된 버튼의 내용과 인덱스를 받아오게 된다.
		parkButton.setBackground(new Color(0xFF, 66, 66)); // 주차중 버튼의 배경을 빨간색으로 지정한다.
		parkButton.setEnabled(false); // 주차된 곳 버튼 비활성화
	}

	class parkCheckListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			codeCheckDialog.setVisible(false);
			parkInputDialog.setVisible(false); // 주차등록이 완료되면 다이어로그를 숨긴다
			parkInputDialog.dispose(); // 등록완료되었으니 등록다이어로그 끄기
			
			//start 가게 해야함
			frame.dispose();
			new ParkingStartView2();
			
		}
	}

	class cancelListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			parkInputDialog.setVisible(false);
			parkInputDialog.dispose(); // 다이어로그 닫기
		}
	} // cancelListener 클래스 End
} // ParkingCarIn class End
