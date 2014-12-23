package com.test.shell;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;

/**
 * Shell����������
 * @author hengzai ʱ�䣺2013-8-2
 */

public class NaviComp extends Composite{
	//�������νṹ����
	private TreeViewer treeViewer;
	
	//�ڲ��� ��ǩ�ṩ�� ��ʾ�ڵ�������Ϣ
	private static class ViewerLabelProvider extends LabelProvider {
		//�õ��ڵ����ʾͼ��
		public Image getImage(Object obj) {
			String imageKey = "./icons/two.png";
			Element element = (Element)obj;
			if(element.getName().equals("node")) {
				imageKey = "./icons/three.png";
			}
			return SWTResourceManager.getImage(imageKey);
		}
		
		//�õ��˽ڵ����ʾ�ı�
		@Override
		public String getText(Object obj) {
			//ǿת ��Element��dom4j��Ԫ�ؽڵ��� dom4j���ڶ�дxml�ļ�
			Element element = (Element)obj;
			//��ýڵ�����
			String text = element.attributeValue("name");
			return text;
		}
	}
	
	//�̳������ṩ���ӿ� �ڲ���
	private static class TreeContentProvider implements ITreeContentProvider {
		
		//������ʱ������
		@Override
		public void dispose() {
		}
		
		//����ı�ʱ���ô˷���
		@Override
		public void inputChanged(Viewer viewer, 
				Object oldInput, Object newInput) {
		
		}
		
		//���û�ѡ��ڵ���ӽڵ�ʱ����ô˷���������һ���ӽڵ�
		@Override
		public Object[] getChildren(Object parentElement) {
			if(parentElement instanceof Element) {
				//elementsȡ�������Ԫ��(List<Element>)���������
				return ((Element)parentElement).elements().toArray(
						new Object[]{});
			}
			return new Object[0];
		}
		
		//����ʼ����ʱ���ȵ��ô˷�������һ�����������
		@Override
		public Object[] getElements(Object inputElement) {
			return getChildren(inputElement);
		}
		
		//�����ĵ�һ��ڵ���ʾ����
		@Override
		public Object getParent(Object child) {
			if(child instanceof Element) {
				return ((Element)child).getParent();
			}
			return null;
		}
		
		//�жϸýڵ��Ƿ����ӽڵ�
		@Override
		public boolean hasChildren(Object parent) {
			if(parent instanceof Element) {
				//���ؽڵ����Ƿ����0�Ľ��
				return ((Element)parent).nodeCount() > 0;
			}
			return false;
		}
	}
	
	//���췽�� �������Composite
	public NaviComp(Composite parent, int style) {
		super(parent, style);
		//���ò��� ˮƽ���
		setLayout(new FillLayout(SWT.HORIZONTAL));
		
		treeViewer = new TreeViewer(this, SWT.NONE);
		treeViewer.setLabelProvider(new ViewerLabelProvider());
		treeViewer.setContentProvider(new TreeContentProvider());
		//������������
		treeViewer.setInput(createDummyModel());
		
//		Test test = new Test();
//		Element node = test.testing();
//		treeViewer.setInput(node);
		
		//չ����
		treeViewer.expandAll();
	}

	public TreeViewer getTreeViewer() {
		return treeViewer;
	}
	
	/**
	 * ��ȡ�ļ����ݲ�ת����Element����
	 * @return
	 */
	private Element createDummyModel() {
		//��ʼ������
		Element root = null;
		try {
			//XML������
			SAXReader reader = new SAXReader();
			//�ļ������� ��ȡ�ù���Ŀ¼�µ�xml�ļ�
			FileInputStream fs = new FileInputStream("./menu.xml");
			//����һ��xml�ĵ�
			Document doc = reader.read(fs);
			//��ø�Ԫ��
			root = doc.getRootElement();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return root;
	}
	
	//��ֹ����Subclassing not allowed�쳣
	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}
}
