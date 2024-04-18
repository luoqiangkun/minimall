Page({
  data: {
    mainActiveIndex: 0,
    activeId: null,
    treeData: [  
      {  
        id: 1,  
        text: '分组 1111111',  
        children: [  
          {  
            id: 4,  
            text: '选项 1-1',  
            children: [] // 没有子节点的节点可以省略 children 属性或者设置为空数组  
          },  
          {  
            id: 5,  
            text: '选项 1-2',  
            children: []  
          }  
        ]  
      },  
      {  
        id: 2,  
        text: '分组 222',  
        children: [  
          {  
            id: 6,  
            text: '选项 2-1',  
            // 假设这个节点还有子节点  
            children: [  
              {  
                id: 9,  
                text: '子选项 2-1-1',  
                children: []  
              }  
            ]  
          },  
          {  
            id: 7,  
            text: '选项 2-2',  
            children: []  
          }  
        ]  
      },  
      {  
        id: 3,  
        text: '分组 3',  
        children: [  
          {  
            id: 8,  
            text: '选项 3-1',  
            children: []  
          }  
        ]  
      },
      {  
        id: 4,  
        text: '分组 4',  
        children: [  
          {  
            id: 8,  
            text: '选项 4-1',  
            children: []  
          }  
        ]  
      } ,
      {  
        id: 5,  
        text: '分组 5',  
        children: [  
          {  
            id: 9,  
            text: '选项 5-1',  
            children: []  
          }  
        ]  
      },
      {  
        id: 6,  
        text: '分组 6',  
        children: [  
          {  
            id: 10,  
            text: '选项 6-1',  
            children: []  
          }  
        ]  
      },
      {  
        id: 6,  
        text: '分组 6',  
        children: [  
          {  
            id: 10,  
            text: '选项 6-1',  
            children: []  
          }  
        ]  
      } ,
      {  
        id: 6,  
        text: '分组 6',  
        children: [  
          {  
            id: 10,  
            text: '选项 6-1',  
            children: []  
          }  
        ]  
      }  ,{  
        id: 6,  
        text: '分组 6',  
        children: [  
          {  
            id: 10,  
            text: '选项 6-1',  
            children: []  
          }  
        ]  
      }  ,{  
        id: 6,  
        text: '分组 6',  
        children: [  
          {  
            id: 10,  
            text: '选项 6-1',  
            children: []  
          }  
        ]  
      }  ,{  
        id: 6,  
        text: '分组 6',  
        children: [  
          {  
            id: 10,  
            text: '选项 6-1',  
            children: []  
          }  
        ]  
      }  ,{  
        id: 6,  
        text: '分组 6',  
        children: [  
          {  
            id: 10,  
            text: '选项 6-1',  
            children: []  
          }  
        ]  
      }  ,{  
        id: 56,  
        text: '分组 6',  
        children: [  
          {  
            id: 10,  
            text: '选项 6-1',  
            children: []  
          }  
        ]  
      }  ,{  
        id: 57,  
        text: '分组 6',  
        children: [  
          {  
            id: 10,  
            text: '选项 6-1',  
            children: []  
          }  
        ]  
      }  ,{  
        id: 58,  
        text: '分组 6',  
        children: [  
          {  
            id: 10,  
            text: '选项 6-1',  
            children: []  
          }  
        ]  
      }  ,
      ,{  
        id: 59,  
        text: '分组 6',  
        children: [  
          {  
            id: 10,  
            text: '选项 6-1',  
            children: []  
          }  
        ]  
      }  ,
      ,{  
        id: 60,  
        text: '分组 6',  
        children: [  
          {  
            id: 10,  
            text: '选项 6-1',  
            children: []  
          }  
        ]  
      }  ,
      ,{  
        id: 61,  
        text: '分组 6',  
        children: [  
          {  
            id: 10,  
            text: '选项 6-1',  
            children: []  
          }  
        ]  
      }  ,
      ,{  
        id: 62,  
        text: '分组 6',  
        children: [  
          {  
            id: 10,  
            text: '选项 6-1',  
            children: []  
          }  
        ]  
      }  ,
      ,{  
        id: 63,  
        text: '分组 6',  
        children: [  
          {  
            id: 10,  
            text: '选项 6-1',  
            children: []  
          }  
        ]  
      }  ,
      ,{  
        id: 64,  
        text: '分组 6',  
        children: [  
          {  
            id: 10,  
            text: '选项 6-1',  
            children: []  
          }  
        ]  
      }  ,
      ,{  
        id: 65,  
        text: '分组 6',  
        children: [  
          {  
            id: 10,  
            text: '选项 6-1',  
            children: []  
          }  
        ]  
      }  ,
      ,{  
        id: 66,  
        text: '分组 6',  
        children: [  
          {  
            id: 10,  
            text: '选项 6-1',  
            children: []  
          }  
        ]  
      }  ,
      ,{  
        id: 67,  
        text: '分组 6',  
        children: [  
          {  
            id: 10,  
            text: '选项 6-1',  
            children: []  
          }  
        ]  
      }  
    ]
  },
  onClickNav: function ({ detail = {} }) {
    this.setData({
      mainActiveIndex: detail.index || 0,
    });
  },


  onClickItem: function({ detail = {} }) {
    const { activeId } = this.data;

    const index = activeId.indexOf(detail.id);
    if (index > -1) {
      activeId.splice(index, 1);
    } else {
      activeId.push(detail.id);
    }

    this.setData({ activeId });
  },
});
