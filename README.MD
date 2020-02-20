
#DRAWIO的改造

##1.修改不显示弹出框： 

App.js 3230  App.prototype.showSplash
 
##2.修改默认的语言为zh
js/diagramly/Init.js:86

##3.修改右上角多语言属性

js/diagramly/Menus.js:905
##4.顶部编辑栏的修改

js/mxgraph/Menus.js:34

##5.文件菜单的子菜单
js/mxgraph/Menus.js:486
js/diagramly/Menus.js:3276
js/diagramly/Menus.js:2631

##6.系统初始化添加$BWK变量保存
js/diagramly/EditorUi.js:9091
##7.去掉底部的sheet页
 
  js/diagramly/Pages.js:1154
##8.删除未保存的提示 

js/diagramly/DrawioFile.js:1413
##9.修改新建文件的后缀名

js/diagramly/Dialogs.js:2647
##10.删除drawio的图片

js/diagramly/App.js:5683

##11.添加自定义图案
web/js/mxgraph/Sidebar.js
添加了自定义图案，div 中可添加id 和class 等自定义属性可按照此思路将全景中需要的元素存入数据库，根据元素id或其他自定义属性关联业务实体，在全景中系统中通过js跨ifream操作div实现动态数据显示
Sidebar.prototype.addSystemPalette 


##12.部署方式
全景项目和框图项目通过nginx 代理至同一域名解决 在全景项目中用ifream 的方式嵌入框图项目而产生的js不能跨域操作访问问题。

### 其他
``` 
1.打开已保存入数据的地图
    http://localhost:8081/t1_war_exploded/dataBaseServlet?method=getHtml&id=22
2.drawdb-修改标记.docx
```