package StartView;

import StartProgram.*;
import StartView.*;
import MainFunctionView.*;

import java.io.*;

import java.awt.*;
import java.awt.event.*;
import java.util.*;

import javax.swing.*;
import javax.swing.event.*;

import FileManeger.FileSystem;
import Information.*;

import javax.swing.border.LineBorder;

public class ParkingStartView2 extends JFrame {
	ParkingStartView2 frame;
	Container frameContentPane; // 전체화면인 프레임 위에 이미 생성되어 있는 컨텐트팬을 저장하기 위함, 이 컨텐트팬 위에서 GUI컴포넌트를 부착해야한다. (유의!)

	// 주차 로고 이미지가 움직이게 하기 위한 변수
	JLabel moveLabel = new JLabel(); // 움직이는 이미지를 담을 Label
	JLabel moveLabel2 = new JLabel();
	JLabel moveLabel3 = new JLabel();
	int x , x2 , x3;
	int y, y2, y3;
	int xdir, xdir2, xdir3;
	int ydir, ydir2, ydir3;
	ImageIcon moveCarImgIcon, moveCarImgIcon2, moveCarImgIcon3;
	Image moveCarImg, moveCarImg2 , moveCarImg3;
	Thread thread, thread2, thread3;

	boolean threadFlag = true;  // 다음화면으로 넘어갈 때 실행중인 스레드를 정지시키고 넘어가게한다.
	boolean threadFlag2 = true;
	boolean threadFlag3 = true;
	
	
	// 타이틀 이미지
	JLabel titleImgLabel = new JLabel();
	ImageIcon titleImgIcon; // 이미지를 가져올 ImageIcon배열 선언
	Image changeSize; // 원본이미지의 크기를 바꿀 Image클래스 객체

	// 입차,출차 버튼
	JButton enterBtn = new JButton(); // 입차
	ImageIcon enterImgIcon = new ImageIcon("entercar.png");
	JButton exitBtn = new JButton(); // 출차
	ImageIcon exitCarImg = new ImageIcon("exitcar.png");

	public ParkingStartView2() {
		this("주차관리시스템"); // 
	} // ParkingStartView2222() 생성자 End

	public ParkingStartView2(String name) // 프레임의 이름을 설정하면서 프레임을 생성하는 생성자다!
	{
		super(name); // 프레임 생성시 제목을 주기위함, ParkingStart에서 new ParkingLogin() 생성 시 제목추가!
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // 프레임의 X를 눌렀을 시 종료
		setBounds(50, 50, 1200, 700); // 프레임이 나타나는 위치와 크기 지정
		setResizable(false); // 화면을 늘이고 줄일 수 없게 프레임 크기를 고정시킨다.

		frameContentPane = getContentPane(); // 현재 생성된 프레임의 컨텐트팬을 저장한다. 이 컨텐트 팬 위에 컴포넌트들을 add하여 화면에 보이게 한다.
		frameContentPane.setLayout(null); // 배치관리자를 null로! (내가 좌표로 마음대로 설정가능하다.) 
		frameContentPane.setBackground(Color.WHITE); // 생성된 프레임의 배경색을 하얀색으로 지정한다

		// 입차, 출차 버튼 actionListener
		enterBtn.addActionListener(new EnterAction());
		exitBtn.addActionListener(new ExitAction());

		titleImage();
		buttons();
		MakeMoveCar(); // 스타트 화면에서 자동차 로고 이미지가 자동으로 움직이게 하는 클래스의 객체를 생성한다.
		
		setVisible(true); // ParkingStartView2222가 상속받은 JFrame의 ContentPane On!

	} // ParkingStartView2222 생성자 End

	public void MakeMoveCar() // 로고 이미지가 프레임 화면 안에서 움직이게 하는 메소드
	{
		// 로고 이미지를 담은 Label을 움직이게 하기 위한 좌표 설정 값들이다
		thread = new Thread(new Runnable() {
			@Override
			public void run() {

				x = 0;
				y = 250;
				xdir = 1;
				ydir = 2;

				frameContentPane.add(moveLabel); // 현재 프레임의 컨텐트 팬에 로고 이미지를 담는 Label을 add한다.
				moveLabel.setBounds(x, y, 70, 70); // 로고 이미지를 담은 Label의 위치와 크기 지정
				moveCarImgIcon = new ImageIcon("bluecar.png"); // 로고 이미지를 가져와서
				moveCarImg = moveCarImgIcon.getImage(); // Image객체 변수에 담아 사이즈를 조정
				moveCarImg = moveCarImg.getScaledInstance(70, 70, java.awt.Image.SCALE_SMOOTH); // 이미지 사이즈 조정
				moveCarImgIcon = new ImageIcon(moveCarImg); // 사이즈를 조정한 이미지를 다시 담아온다.
				moveLabel.setIcon(moveCarImgIcon); // Label에 이미지를 넣는다.

				while (threadFlag) { // Flag가 true일때만 스레드가 실행된다.
					// TODO Auto-generated method stub
					if (x == 0) { // 왼쪽 위에서 아래로 이미지 이동
						y += ydir;
					}
					if (y == 600) { // 왼쪽 아래에서 오른쪽으로 이미지 이동
						x += xdir;
					}
					if (x == 1120) { // 오른쪽 아래에서 위로 이미지 이동
						y -= ydir;
					}
					if (y == 0) { // 오른쪽 위에서 왼쪽으로 이미지 이동
						x -= xdir;
					}

				moveLabel.setLocation(x, y); // 이미지를 담은 Label이 프레임 화면에서 왔다 갔다 하게 설정한다.
					try {
						Thread.sleep(4);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		});
		thread2 = new Thread(new Runnable() {
			@Override
			public void run() {

				x2 = 1120;
				y2 = 600;
				xdir2 = 1;
				ydir2 = 2;

				frameContentPane.add(moveLabel2); // 현재 프레임의 컨텐트 팬에 로고 이미지를 담는 Label을 add한다.
				moveLabel2.setBounds(x2, y2, 70, 70); // 로고 이미지를 담은 Label의 위치와 크기 지정
				moveCarImgIcon2 = new ImageIcon("pinkcar.png"); // 로고 이미지를 가져와서
				moveCarImg2 = moveCarImgIcon2.getImage(); // Image객체 변수에 담아 사이즈를 조정
				moveCarImg2 = moveCarImg2.getScaledInstance(70, 70, java.awt.Image.SCALE_SMOOTH); // 이미지 사이즈 조정
				moveCarImgIcon2 = new ImageIcon(moveCarImg2); // 사이즈를 조정한 이미지를 다시 담아온다.
				moveLabel2.setIcon(moveCarImgIcon2); // Label에 이미지를 넣는다.

				while (threadFlag2) { // Flag가 true일때만 스레드가 실행된다.

					// TODO Auto-generated method stub
					if (x2 == 1120) { // 오른쪽 아래에서 위로 이미지 이동
						y2 -= ydir2;
					}
					if (y2 == 0) { // 오른쪽 위에서 왼쪽으로 이미지 이동
						x2 -= xdir2;
					}
					if (x2 == 0) { // 왼쪽 위에서 아래로 이미지 이동
						y2 += ydir2;
					}
					if (y2 == 600) { // 왼쪽 아래에서 오른쪽으로 이미지 이동
						x2 += xdir2;
					}

				moveLabel2.setLocation(x2, y2); // 이미지를 담은 Label이 프레임 화면에서 왔다 갔다 하게 설정한다.
					try {
						Thread.sleep(5);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		});
		thread3 = new Thread(new Runnable() {
			@Override
			public void run() {

				x3 = 0;
				y3 = 0;
				xdir3 = 1;
				ydir3 = 2;

				frameContentPane.add(moveLabel3); // 현재 프레임의 컨텐트 팬에 로고 이미지를 담는 Label을 add한다.
				moveLabel3.setBounds(x3, y3, 70, 70); // 로고 이미지를 담은 Label의 위치와 크기 지정
				moveCarImgIcon3 = new ImageIcon("yellowcar.png"); // 로고 이미지를 가져와서
				moveCarImg3 = moveCarImgIcon3.getImage(); // Image객체 변수에 담아 사이즈를 조정
				moveCarImg3 = moveCarImg3.getScaledInstance(70, 70, java.awt.Image.SCALE_SMOOTH); // 이미지 사이즈 조정
				moveCarImgIcon3 = new ImageIcon(moveCarImg3); // 사이즈를 조정한 이미지를 다시 담아온다.
				moveLabel3.setIcon(moveCarImgIcon3); // Label에 이미지를 넣는다.

				while (threadFlag3) { // Flag가 true일때만 스레드가 실행된다.
					// TODO Auto-generated method stub
					if (x3 == 0) { // 왼쪽 위에서 아래로 이미지 이동
						y3 += ydir3;
					}
					if (y3 == 600) { // 왼쪽 아래에서 오른쪽으로 이미지 이동
						x3 += xdir3;
					}
					if (x3 == 1120) { // 오른쪽 아래에서 위로 이미지 이동
						y3 -= ydir3;
					}
					if (y3 == 0) { // 오른쪽 위에서 왼쪽으로 이미지 이동
						x3 -= xdir3;
					}

				moveLabel3.setLocation(x3, y3); // 이미지를 담은 Label이 프레임 화면에서 왔다 갔다 하게 설정한다.
					try {
						Thread.sleep(2);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		});
		thread.start();
		thread2.start();
		thread3.start();
	} // MakeMoveCar() End

	public void titleImage() {

		titleImgIcon = new ImageIcon("parkingIcon.png"); // ImgIcon에 각각의 이미지를 집어넣는다.
		changeSize = titleImgIcon.getImage();
		changeSize = changeSize.getScaledInstance(700, 250, java.awt.Image.SCALE_SMOOTH);
		titleImgIcon = new ImageIcon(changeSize);
		titleImgLabel.setIcon(titleImgIcon);
		titleImgLabel.setBounds(250, 80, 700, 250); // 라벨 위치와 크기 (이미지의 크기도 라벨의 크기만큼 바뀐다.)
		frameContentPane.add(titleImgLabel); // 컨텐트팬에 이미지넣을 라벨 추가

	}

	public void buttons() {
		enterBtn.setBounds(240, 400, 300, 150);
		enterImgIcon = changeSize(enterImgIcon);
		enterBtn.setIcon(enterImgIcon);
		frameContentPane.add(enterBtn);

		exitBtn.setBounds(650, 400, 300, 150);
		exitCarImg = changeSize(exitCarImg);
		exitBtn.setIcon(exitCarImg);
		frameContentPane.add(exitBtn);

	}

	public ImageIcon changeSize(ImageIcon imgIcon) { // 버튼 패널 버튼들의 이미지를 변환시키는 메소드다.
		Image chaImg = imgIcon.getImage();
		chaImg = chaImg.getScaledInstance(300, 150, java.awt.Image.SCALE_SMOOTH);
		imgIcon = new ImageIcon(chaImg);
		return imgIcon;
	}

	class EnterAction implements ActionListener { // '입차' 버튼 클릭 시

		public void actionPerformed(ActionEvent e) {
			threadFlag = false; // 움직이는 자동차 스레드 정지.
			new ParkingMainView(ParkingStartView2.this);
		}
	}

	class ExitAction implements ActionListener { // '출차' 버튼 클릭 시

		public void actionPerformed(ActionEvent e) {
			threadFlag = false; // 움직이는 자동차 스레드 정지.
			new out(ParkingStartView2.this);
		}
	}
} // ParkingStartView2222 class End