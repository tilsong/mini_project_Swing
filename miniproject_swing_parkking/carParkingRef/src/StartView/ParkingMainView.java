package StartView;

import StartProgram.*;
import StartView.*;
import MainFunctionView.*;

import java.awt.*;
import java.awt.event.*;
import java.text.*;
import java.util.*;
import java.util.List;

import javax.swing.*;
import javax.swing.event.*;
import javax.swing.table.DefaultTableModel;

import FileManeger.FileSystem;
import Information.ParkCarInfo;
import MainFunctionView.Test;
public class ParkingMainView {
	List<Integer> currentParkPlace;
	Test t = new Test();
	
	ParkingStartView2 frame; // ParkingStartView에서 생성된 JFrame를 받기 위한 변수
	JPanel parkingMainFullScreen = new JPanel(null); // ParkingStartView에서 생성된 JFrame의 ContentPane을 여기서 JPanel로 변경하여 화면이
														// 넘어가게 한다.
	// 로고이미지를 상단에 넣기 위한 컴포넌트
	ImageIcon logoImgIcon;
	JLabel logoImgLabel = new JLabel();
	Image changeSizeImg; // ImageIcon 객체로는 사이즈를 변경하기가 힘들다. Image객체를 이용해서 이미지 사이즈를 변경한다.

	// 날짜와 시간을 담기 위한 컴포넌트
	ImageIcon timeImgIcon;
	JLabel timeImgLabel;
	JLabel pDateLabel = new JLabel(); // 연도, 월, 일을 담기 위한 Label
	JLabel pTimeLabel = new JLabel(); // 시간을 담기 위한 Label

	// 주차 요금 안내판
	ImageIcon feeInfoIcon;
	JLabel feeInfoImgLabel = new JLabel();

	// 이미지 아래부분에 주차공간(버튼)을 생성한다.
	CardLayout cardLayout = new CardLayout(); // 주차공간의 레이아웃은 CardLayout이어야한다.
	JPanel cardPanel = new JPanel(cardLayout); // 중간에 삽입 될 주차공간 패널을 통제하는 패널로 카드레이아웃이 필요하다.
	JPanel parkFloorPanel = new JPanel(); // 

	// ActionEvent에 쓰일 버튼을 저장한 JButton클래스의 객체 배열 선언 ,출차할 때 쓰기위해 static으로 선언
	public static JButton[] parkButton = new JButton[20];

	public ParkingMainView(ParkingStartView2 frame) {
		this.frame = frame; // 전송한 ParkingStartView의 frame을 받아온다.
		this.frame.setContentPane(parkingMainFullScreen); // ParkingStartView의 ContentPane이었던 것을 현재 클래스의 JPanel로 변경함으로서
															// 화면을 바꾼다. 화면 전환은 이렇게 하자!!!
		parkingMainFullScreen.setBackground(Color.WHITE);
		view(); // 주차시스템 메인화면을 구성한 view()메소드 호출

		currentParkPlace=t.parkSetting();
		for(int i =0; i<currentParkPlace.size(); i++) {
			System.out.println(currentParkPlace.get(i));
		}
		
		for (int n : currentParkPlace) { // 파일에서 읽어온 리스트의 내용으로 주차차량 정보를 가져와 주차시스템에 적용시킨다. (주차되어있는 공간은
															// 계속 빨간색 표시!)
			JButton complete = ParkingMainView.parkButton[n-1]; // 주차완료 시에 클릭했던 버튼의 정보를 받아온다.
			complete.setText("주차중"); // 프로그램을 껏다 켯을시에 주차되어있는 부분이 초기화되지 않도록 파일에 저장된 List의 내용을 토대로 다시 주차중으로 바꿔준다
			complete.setBackground(new Color(0xFF, 66, 66)); // 주차중의 배경색은 빨간색
			complete.setEnabled(false); // 읽어온 데이터대로 주차중인 곳은 비활성화 처리한다.
		}
		
	} // ParkingMainView 생성자 End

	
	public void makeParkPlaceBtn() {// 주차공간 버튼을 구현하는 메소드다. 1~3층으로 구성했으며 버튼 클릭시 같은 공간에 같은 버튼들이 보여야해서 CardLayout으로
									// 설정했다.
		parkFloorPanel = new JPanel(new GridLayout(3, 10)); // 각 패널에 3행 10열의 배치관리자를 가진 패널객체를 생성한다.
		cardPanel.add(parkFloorPanel); // CardLayout으로 되어있는 cardPanel에 각 층의 주차공간을 나타내는 패널 추가!, 각 카드패널이름을 1, 2,3으로 지정

		parkingMainFullScreen.add(cardPanel);
		cardPanel.setBounds(5, 200, 870, 465); // 카드 레이아웃 위치와 크기 지정

		int buttonCount = 1; // 각 버튼 배열의 인덱스를 지정하기 위한 변수
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 10; j++) {
				if (i % 2 == 0) {
					// 각 층에 버튼(2o개)을 생성하고 그 버튼을 JButton배열에 저장한다 , 생성시 색을 연두색으로 설정
					parkButton[buttonCount - 1] = (JButton) parkFloorPanel.add(new JButton("" + buttonCount));
					parkButton[buttonCount - 1].setBackground(new Color(109, 171, 217)); // 버튼색은 연두색
					buttonCount++;
				} else // 10개 주차공간 생성 후 다음 라인은 공백으로!
				{
					parkFloorPanel.add(new Label()); // 주차공간 사이 중간에 공백을 넣기 위함
				}
			}
		}
		
		for (int i = 0; i < parkButton.length; i++) { // 각 버튼의 리스너를 구현한 클래스로 익명클래스, 즉 각기 다른 객체 생성으로 핸들러 처리
			parkButton[i].addActionListener(new ParkingCarIn(frame)); // 버튼 클릭
			// 시 발생하는 이벤트를 처리하기 위해 리스너를 구현한 클래스 객체를 생성
		}
	}
	// makeParkPlaceBtn() End

	public void view() {// 주차시스템 메인화면을 구성한 메소드 들을 호출한다.
		makeParkLogoImg();// 로고이미지 출력
		makeDateTime(); // 맨 위에 나타나는 날짜와 시간
		makeTimeImg(); // 시간이 들어갈 이미지
		makeFeeInfo(); // 주차요금 안내판
		makeParkPlaceBtn(); // 주차공간 버튼을 구현
	} // view() End

	// 228 1941 수정 . 그림은 잘 나옴
	public void makeParkLogoImg() // 상단에 로고이미지를 띄우는 메소드
	{
		logoImgIcon = new ImageIcon("main_head.png");
		changeSizeImg = logoImgIcon.getImage(); // 로고이미지의 크기를 변환하기 위해 Image클래스 객체를 이용한다.
		changeSizeImg = changeSizeImg.getScaledInstance(875, 200, java.awt.Image.SCALE_SMOOTH); // 로고이미지의 크기를 140, 140으로
																								// 변환시킨다.
		logoImgIcon = new ImageIcon(changeSizeImg); // 변환된 크기의 이미지를 ImageIcon에 담는다.
		logoImgLabel.setIcon(logoImgIcon);
		logoImgLabel.setBounds(3, 0, 875, 200); // 로고이미지를 담은 Label의 위치와 크기 설정
		parkingMainFullScreen.add(logoImgLabel); // 로고이미지를 담은 Label을 컨텐트팬에 붙인다.
	}

	// 228 2056 수정 시계 폰트 및 출력 내용 수정
	public void makeDateTime() {
		pDateLabel.setFont(new Font("돋움체", Font.BOLD, 25)); // 현재 연도, 월, 일의 글씨체와 색지정
		// pDateLabel.setBounds(930, 60, 300, 30); //DATE라벨 크기 및 위치 지정

		pTimeLabel.setFont(new Font("돋움체", Font.BOLD, 35)); // 현재 시간의 글씨체와 색지정
		Thread thread = new Thread(new Runnable() { // 현재시간을 동작하기 위한 스레드, 익명클래스 기법을 사용하여 Runnable인터페이스의 run()메소드
														// 오버라이드 하여 구현
			@Override
			public void run() {
				while (true) {
					Calendar presentTime = Calendar.getInstance(); // 현재 시간을 가져온다.
					SimpleDateFormat format = new SimpleDateFormat("HH" + "시 " + "mm" + "분 " + "ss" + "초"); // 시간 출력 형식 지정
					try {
						String date = presentTime.get(Calendar.YEAR) + "년 " + (presentTime.get(Calendar.MONTH) + 1)
								+ "월 " + presentTime.get(Calendar.DATE) + "일"; // 가져온 시간의 연도, 월, 일을 저장한다.
						String time = format.format(presentTime.getTime()); // 현재 시간을 가져와서 format1형식에 맞게 String에 저장
						pDateLabel.setText(date); // pDate Label에 연월일 출력
						pTimeLabel.setText(time); // pTime Label에 시간 출력
						Thread.sleep(1000); // 시계가 동작하듯이 1초마다 동작하게끔 설정
					} catch (InterruptedException e) {
					}
				}
			}
		}); // Time을 나타내는 Thread End
		thread.start();// 스레드 - 실시간 시간반영
	} // makeDateTime() End
	
		// 228 2056 수정 시계 위치 및 배경 이미지 수정

	public void makeTimeImg() { // 시계 이미지를 상단에 넣는다.
		timeImgIcon = new ImageIcon("시계 배경.png");
		changeSizeImg = timeImgIcon.getImage();
		changeSizeImg = changeSizeImg.getScaledInstance(300, 155, java.awt.Image.SCALE_SMOOTH);
		timeImgIcon = new ImageIcon(changeSizeImg);

		timeImgLabel = new JLabel("테스트", timeImgIcon, JLabel.CENTER); // Label을 생성하고 그 안에 시계 Image를 삽입한다.
		timeImgLabel.setBounds(882, 15, 300, 155);

		// 스레드로 구현한 날짜와 시간 출력을 시간 이미지 위에 띄우도록 설정한다.
		timeImgLabel.add(pDateLabel); // 날짜 텍스트를 가진 Label을 timeImg위에 출력
		timeImgLabel.add(pTimeLabel); // 시간 텍스트를 가진 Label을 timeImg위에 출력
		pDateLabel.setBounds(15, 0, 300, 100); // 년,월,일이 나타나는 Label의 위치 지정 , timeImgLabel위에
		pTimeLabel.setBounds(15, 50, 300, 100); // 시, 분, 초가 나타나는 Label의 위치 지정, timeImgLabel위에

		parkingMainFullScreen.add(timeImgLabel);
	}

	public void makeFeeInfo() { // 메인 시스템 화면에 출력하게 될 주차 요금 안내판 이미지이다.
		feeInfoIcon = new ImageIcon("주차 요금 안내.png");
		changeSizeImg = feeInfoIcon.getImage(); // 이미지의 크기를 변환하기 위해 Image객체 변수에 이미지 담기
		changeSizeImg = changeSizeImg.getScaledInstance(300, 240, java.awt.Image.SCALE_SMOOTH); // 원본 이미지의 크기 조정
		feeInfoIcon = new ImageIcon(changeSizeImg); // 크기를 바꾼 이미지를 다시 ImageIcon에 넣는다.
		feeInfoImgLabel.setIcon(feeInfoIcon); // Label에 ImageIcon을 담는다.
		feeInfoImgLabel.setBounds(880, 250, 310, 300); // 이미지가 담긴 Label의 크기와 위치 지정
		parkingMainFullScreen.add(feeInfoImgLabel); // 이미지 Label 패널에 추가
	} // makeFeeInfo() End

} // ParkingMainView class End
