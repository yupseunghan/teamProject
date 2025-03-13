   private static JPanel panelBookmark() {
      JPanel bookMarkPanel = new JPanel(new GridBagLayout());
      GridBagConstraints gbc = new GridBagConstraints();
      gbc.fill = GridBagConstraints.BOTH;
      gbc.weightx = 1.0;
      gbc.weighty = 1.0;
      
      DefaultListModel<String> listModel = new DefaultListModel<>();
      // 여기서 for문으로 listModel.addElement(스트링); 해서 즐겨찾기 등록 
      listModel.add(0, "무한도전");
      listModel.add(1, "1박2일");
        JList<String> list = new JList<>(listModel);
        JScrollPane scrollPane = new JScrollPane(list);

        
        

        gbc.gridx = 0; gbc.gridy = 10; gbc.weighty = 10;
        bookMarkPanel.add(scrollPane, gbc);
      
        // 삭제용 combox(전체 프로그램 가져와서 넣어줘야함)
        String[] pros = { "뉴스9", "1박 2일", "개그콘서트" };
        JComboBox<String> prBox = new JComboBox<>(pros);      JButton bookmarkUpadateButton = new JButton("즐겨찾기 추가");
      
      
      
      
      
      
      // 삭제용 combox(이미 있는애들만)
      String[] bmNames = new String[listModel.getSize()];
      for (int i = 0; i < listModel.getSize(); i++) {
          bmNames[i] = listModel.getElementAt(i);                  //얘는 위에서 가져온 listModel에서 가져옵니다
      }
       JComboBox<String> bmBox = new JComboBox<>(bmNames);

      
      JButton bookmarkdeleteButton = new JButton("즐겨찾기 삭제");
       
       
      JButton backButton = new JButton("뒤로가기");
      
      
      
      addComponent(bookMarkPanel, prBox, 0, 11, gbc);
      addComponent(bookMarkPanel, bookmarkUpadateButton, 0, 12, gbc);
      addComponent(bookMarkPanel, bmBox, 0, 13, gbc);
      addComponent(bookMarkPanel, bookmarkdeleteButton, 0, 14, gbc);
      addComponent(bookMarkPanel, backButton, 0, 15, gbc);
      
      
      bookmarkUpadateButton.addActionListener(e -> {
         JOptionPane.showMessageDialog(bookMarkPanel, "즐겨찾기에 추가");
         panelBookmarkAdd();
      });
      
      bookmarkdeleteButton.addActionListener(e -> {
         JOptionPane.showMessageDialog(bookMarkPanel, "즐겨찾기 삭제하는 메소드");
         panelBookmarkDel();
      });
      backButton.addActionListener(e -> {
         switchPanel("user");
      });
      
      return bookMarkPanel;
      
   }
   
