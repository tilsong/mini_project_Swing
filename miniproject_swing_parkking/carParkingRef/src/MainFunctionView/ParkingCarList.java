package MainFunctionView;

import StartProgram.*;
import StartView.*;
import MainFunctionView.*;

import java.awt.*;
import java.awt.event.*;
import java.util.*;

import javax.swing.*;
import javax.swing.event.*;
import javax.swing.table.DefaultTableModel;

public class ParkingCarList implements ActionListener {
	ParkingStartView2 frame; // ParkingStartView객체의 JFrame을 가져오기 위한 변수
	
	// 기본적으로 JTable을 사용하는 방법
	Vector<String> rowData; // 주차 목록이 담긴 벡터의 내용을 담기 위한 벡터 변수
	Vector<String> colName = new Vector<String>(); // 테이블의 열 제목을 저장하기 위한 벡터변수

	DefaultTableModel parkTableModel = new DefaultTableModel(); // DefaultTabelModel (vector, vector)형태로 사용예정
	JTable parkJTable; // DefaultTableModel을 담기 위한 JTable변수, JTable의 인자 중 하나가 TableModel이다
	public static JScrollPane parkScroll; // JTable에 Scroll기능을 달기 위한 JScroll변수, '주차현황'클릭 시 '주차내역'테이블이 보이지 않아야하므로 서로
											// 상호작용하기 위해 static로 설정

	public ParkingCarList(ParkingStartView2 frame) {
		this.frame = frame; // ParkingStartView에서 가져온 frame을 저장한다.

		// JTable 모양, 크기 지정 및 위치 지정
		colName.addElement("주차 내역 출력"); // colName벡터에 내용저장 (테이블의 제목이라고 보면된다)
		parkTableModel = new DefaultTableModel(null, colName); // DefaultTableModel의 row(열)내용인 null에도 벡터가 들어갈 예정이다.
		parkJTable = new JTable(parkTableModel); // JTable의 생성자 인자에 TableModel을 넣어줘야해서 DefaultTableModel을 사용한다
		parkScroll = new JScrollPane(parkJTable); // JTable 오른쪽에 스크롤바를 붙인다!
		parkScroll.setBounds(880, 200, 310, 240); // 출력 테이블 위치와 크기 지정

		parkScroll.setVisible(false);
		frame.add(parkScroll); // ParkingStartView의 frame에 JTable을 배치한다!
		thread.start(); // 객체 생성과 동시에 '주차내역'을 최신화하는 스레드를 실행시킨다.
	} // ParkingCarList 생성자 End

	// '주차내역' 클릭 시 발생한 이벤트 처리 핸들러
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
		parkScroll.setVisible(true); // '주차내역' 출력
	}

	Thread thread = new Thread(new Runnable() { // '주차내역'테이블에 출력될 내용들이 자동으로 수정되게끔 설정한다.
		@Override
		public void run() {
			// TODO Auto-generated method stub
			while (true) {
				// 추가된 내용까지 합쳐서 다시 출력하기 위해 먼저 Table에 들어가있는 내용을 전부 지운다.
				int rowCount = parkTableModel.getRowCount();
				for (int i = rowCount - 1; i > -1; i--) {
					parkTableModel.removeRow(i);
				}
				for (String parkData : ParkingCarIn.parkPrintList) {
					rowData = new Vector<String>(); // 벡터 생성
					rowData.addElement(parkData); // 생성한 벡터에 스트링 값 저장
					parkTableModel.addRow(rowData); // 테이블의 행에 스트링 값(주차목록)을 하나씩 추가한다.
				}
				try {
					thread.sleep(1000);
				} catch (InterruptedException e) {
				}
			} // while End
		} // run() End
	}); // Thread End
}
