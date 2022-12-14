/*
1) 버튼, 또는 마우스 클릭을 통해 레이더를 생성하고 삭제할 수 있도록 구현
 - 레이더의 개수는 최소 3개 이상 생성할 수 있도록 구현
 - 최대 개수는 자유롭게 하되 그래픽이 자연스러운 범위 내에서 생성하도록 할 것 
2) 각 레이더(원)는 프레임(또는 패널)내에서 이동해야 함
 - 방향은 자유롭게 움직이되 연속적인 움직임이 되도록 구현(특정 좌표에서 갑자기 관계 없는 위치에 나타나는 이동은 안됨)
 - 직선이나 곡선의 이동 궤적 모두 사용 가능
3) 레이더는 생성될 때 마다 다른 색상을 가지도록 구현할 것
 - 레이더 상에 나타나는 점은 다른 레이더의 색상을 반영할 것
 - 간혹 중복되는 색상은 무방함 
4) 각 레이더는 그림과 같이 상대 레이더의 위치가 자신의 중점을 기준으로 레이더 영역 내에 식별되는 색상으로 나타나도록 할 것
 - 거리는 수식으로 정교하게 계산하거나, 또는 적절하게 보이는 수준으로 하는 것 모두 사용 가능
 - 단 직관적으로 봤을 때 상대 레이더의 위치가 납득되는 수준으로 출력 되어야 함
 */

package project;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Timer;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

class Radar extends JFrame {

	JButton btn1 = new JButton("추가");
	JButton btn2 = new JButton("삭제");
	JPanel panel = new MyPanel();

	List<Integer> list_x = new ArrayList<>();
	List<Integer> list_y = new ArrayList<>();
	List<Float> list_r = new ArrayList<>();
	List<Float> list_g = new ArrayList<>();
	List<Float> list_b = new ArrayList<>();

	Random rand = new Random();

	float r1 = rand.nextFloat();
	float g1 = rand.nextFloat();
	float b1 = rand.nextFloat();

	Color black = new Color(0, 0, 0);
	Color white = new Color(255, 255, 255);

	int x = (int) (Math.random() * 1500) + 1;
	int y = (int) (Math.random() * 1000) + 1;
	int w = 200;
	int h = 200;
	int num = 0;
	int k1 = 30;

	class MyListener_plus implements ActionListener {

		public void actionPerformed(ActionEvent e) {
			
			repaint();
			x = (int) (Math.random() * 1500) + 1;
			y = (int) (Math.random() * 1000) + 1;
			r1 = rand.nextFloat();
			g1 = rand.nextFloat();
			b1 = rand.nextFloat();

			list_r.add(r1);
			list_g.add(g1);
			list_b.add(b1);

			while (true) {
				x = (int) (Math.random() * 1500) + 1;
				y = (int) (Math.random() * 1000) + 1;
				if (((x <= 1400) & (x >= 100)) & ((y <= 700) & (y >= 100)))
					break;
			}

			list_x.add(x);
			list_y.add(y);

			num++;
			System.out.println("<" + num + "번 수정됨>");
			System.out.println("x는 : " + list_x);
			System.out.println("y는 : " + list_y);

			
		}
	}

	class MyListener_delete implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			
			revalidate();
			repaint();
			list_x = new ArrayList<>();
			list_y = new ArrayList<>();
			list_r = new ArrayList<>();
			list_g = new ArrayList<>();
			list_b = new ArrayList<>();

			System.out.println("\n" + num + "번 삭제됨\n");
			num = 0;

		}
	}
	class MyPanel extends JPanel {

		public void paintComponent(Graphics g) {
			
			
			Graphics gpnew = getGraphics();
			Graphics gp1 = getGraphics();
			Graphics gp_mid = getGraphics();

			Graphics gpwhite = getGraphics();
			Graphics gpblack = getGraphics();
			
			Color randomColor1 = new Color(r1, g1, b1);
			gp_mid.setColor(black);
			gpwhite.setColor(white);
			gpblack.setColor(black);
			
			for (int d = 0; d<list_x.size(); d++) {
				gp1.setColor(new Color(list_r.get(d), list_g.get(d), list_b.get(d)));
				gp1.fillOval(list_x.get(d), list_y.get(d), w, h);
				gpblack.drawOval(list_x.get(d), list_y.get(d), w, h);
				gp_mid.fillOval(list_x.get(d)+95, list_y.get(d)+95, w-195, h-195);
				gpwhite.drawOval(list_x.get(d)+95, list_y.get(d)+95, w-195, h-195);
			}
			
			for (int i = 0; i < list_x.size(); i++) {

				int t = 0;
				
				if (i == 0) {
					continue;
				}
				
				gp_mid.fillOval(list_x.get(i)+95, list_y.get(i)+95, w-195, h-195);
				gpwhite.drawOval(list_x.get(i)+95, list_y.get(i)+95, w-195, h-195);
			
				gpnew.setColor(new Color(list_r.get(i), list_g.get(i), list_b.get(i)));
				
				while (t < i) {
					if (list_x.get(t) < list_x.get(i)) {
						if (list_y.get(t) < list_y.get(i)) {
							while(k1>15) {
								gpnew.fillOval(list_x.get(t) + 95 + ((list_x.get(i) - list_x.get(t)) / k1),
										list_y.get(t) + 95 + ((list_y.get(i) - list_y.get(t)) / k1), w - 190, h - 190);
								gpwhite.drawOval(list_x.get(t) + 95 + ((list_x.get(i) - list_x.get(t)) / k1),
										list_y.get(t) + 95 + ((list_y.get(i) - list_y.get(t)) / k1), w - 190, h - 190);
								try {
									Thread.sleep(20);
								} catch (InterruptedException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
								k1--;
							}
							t++;
							k1 = 30;
						} else {
							while(k1>15) {
								gpnew.fillOval(list_x.get(t) + 95 + ((list_x.get(i) - list_x.get(t)) / k1),
										list_y.get(t) + 95 + ((-list_y.get(t) + list_y.get(i)) / k1), w - 190, h - 190);
								gpwhite.drawOval(list_x.get(t) + 95 + ((list_x.get(i) - list_x.get(t)) / k1),
										list_y.get(t) + 95 + ((-list_y.get(t) + list_y.get(i)) / k1), w - 190, h - 190);
								try {
									Thread.sleep(20);
								} catch (InterruptedException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
								k1--;
							}
							
							t++;
							k1 = 30;
						}
					} else if (list_x.get(t) >= list_x.get(i)) {
						if (list_y.get(t) < list_y.get(i)) {
							while(k1>15) {
								gpnew.fillOval(list_x.get(t) + 95 + ((-list_x.get(t) + list_x.get(i)) / k1),
										list_y.get(t) + 95 + ((list_y.get(i) - list_y.get(t)) / k1), w - 190, h - 190);
								gpwhite.drawOval(list_x.get(t) + 95 + ((-list_x.get(t) + list_x.get(i)) / k1),
										list_y.get(t) + 95 + ((list_y.get(i) - list_y.get(t)) / k1), w - 190, h - 190);
								try {
									Thread.sleep(20);
								} catch (InterruptedException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
								k1--;
							}
							
							t++;
							k1 = 30;
						} else {
							while(k1>15) {
								gpnew.fillOval(list_x.get(t) + 95 + ((-list_x.get(t) + list_x.get(i)) / k1),
										list_y.get(t) + 95 + ((-list_y.get(t) + list_y.get(i)) / k1), w - 190, h - 190);
								gpwhite.drawOval(list_x.get(t) + 95 + ((-list_x.get(t) + list_x.get(i)) / k1),
										list_y.get(t) + 95 + ((-list_y.get(t) + list_y.get(i)) / k1), w - 190, h - 190);
								try {
									Thread.sleep(20);
								} catch (InterruptedException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
								k1--;
							}
							
							t++;
							k1 = 30;
						}
					}
				}
			}
			for (int j = list_x.size() - 1; j >= 0; j--) {

				int t = list_x.size() - 1;
	
				if (j == list_x.size() - 1) {
					
					continue;
				}

				
				gp_mid.fillOval(list_x.get(j)+95, list_y.get(j)+95, w-195, h-195);
				gpwhite.drawOval(list_x.get(j)+95, list_y.get(j)+95, w-195, h-195);
				
				gpnew.setColor(new Color(list_r.get(j), list_g.get(j), list_b.get(j)));

				while (t > j) {
					if (list_x.get(t) < list_x.get(j)) {
						if (list_y.get(t) < list_y.get(j)) {
							while(k1>15) {
								gpnew.fillOval(list_x.get(t) + 95 + ((list_x.get(j) - list_x.get(t)) / k1),
										list_y.get(t) + 95 + ((list_y.get(j) - list_y.get(t)) / k1), w - 190, h - 190);
								gpwhite.drawOval(list_x.get(t) + 95 + ((list_x.get(j) - list_x.get(t)) / k1),
										list_y.get(t) + 95 + ((list_y.get(j) - list_y.get(t)) / k1), w - 190, h - 190);
								try {
									Thread.sleep(20);
								} catch (InterruptedException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
								k1--;
							}
							
							t--;
							k1 = 30;
						} else {
							while(k1>15) {
								gpnew.fillOval(list_x.get(t) + 95 + ((list_x.get(j) - list_x.get(t)) / k1),
										list_y.get(t) + 95 + ((-list_y.get(t) + list_y.get(j)) / k1), w - 190, h - 190);
								gpwhite.drawOval(list_x.get(t) + 95 + ((list_x.get(j) - list_x.get(t)) / k1),
										list_y.get(t) + 95 + ((-list_y.get(t) + list_y.get(j)) / k1), w - 190, h - 190);
								try {
									Thread.sleep(20);
								} catch (InterruptedException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
								k1--;
							}
							
							t--;
							k1 =30;
						}
					} else if (list_x.get(t) >= list_x.get(j)) {
						if (list_y.get(t) < list_y.get(j)) {
							while(k1>15) {
								gpnew.fillOval(list_x.get(t) + 95 + ((list_x.get(j) - list_x.get(t)) / k1),
										list_y.get(t) + 95 + ((list_y.get(j) - list_y.get(t)) / k1), w - 190, h - 190);
								gpwhite.drawOval(list_x.get(t) + 95 + ((list_x.get(j) - list_x.get(t)) / k1),
										list_y.get(t) + 95 + ((list_y.get(j) - list_y.get(t)) / k1), w - 190, h - 190);
								try {
									Thread.sleep(20);
								} catch (InterruptedException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
								k1--;
							}
							
							t--;
							k1 =30;
						} else {
							while(k1<15) {
								gpnew.fillOval(list_x.get(t) + 95 + ((-list_x.get(t) + list_x.get(j)) / k1),
										list_y.get(t) + 95 + ((-list_y.get(t) + list_y.get(j)) / k1), w - 190, h - 190);
								gpwhite.drawOval(list_x.get(t) + 95 + ((-list_x.get(t) + list_x.get(j)) / k1),
										list_y.get(t) + 95 + ((-list_y.get(t) + list_y.get(j)) / k1), w - 190, h - 190);
								try {
									Thread.sleep(20);
								} catch (InterruptedException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
								k1--;
							}
							
							t--;
							k1 = 30;
						}
					}
				}
			}
			
		}
	}

	public Radar() {
		setSize(800, 800);
		setTitle("Java Radar");
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// Timer timer = new Timer();
		btn1.addActionListener(new MyListener_plus());
		btn2.addActionListener(new MyListener_delete());
		panel.add(btn1);
		panel.add(btn2);
		
		add(panel);
	}

	public static void main(String args[]) {
		Radar R = new Radar();
	}
}
