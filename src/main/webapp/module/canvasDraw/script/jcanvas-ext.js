var layer=0;    //图层索引  
CanvasExt={
	drawRect:function(canvasId,penColor,strokeWidth){  
           var that=this;  
           that.penColor=penColor;  
           that.penWidth=strokeWidth;  
             
           var canvas=document.getElementById(canvasId);  
            var canvasRect = canvas.getBoundingClientRect();  
            var canvasLeft=canvasRect.left;  
            var canvasTop=canvasRect.top;  
              
            var layerIndex=layer;  
            var layerName="layer";  
            var x=0;  
            var y=0;  
              
            canvas.onmousedown=function(e){  
                var color=that.penColor;  
                var penWidth=that.penWidth;  
                  
                layerIndex++;  
                layer++;  
                layerName+=layerIndex;  
                x=e.clientX-canvasLeft;  
                y=e.clientY-canvasTop;  
                  
                $("#"+canvasId).addLayer({  
                    type: 'rectangle',  
                    strokeStyle: color,  
                    strokeWidth: penWidth,  
                     name:layerName,  
                     fromCenter: false,  
                     x: x, y: y,  
                      width: 1,  
                      height: 1  
                });  
  
                $("#"+canvasId).drawLayers();  
                $("#"+canvasId).saveCanvas();  
                canvas.onmousemove=function(e){  
                    width=e.clientX-canvasLeft-x;  
                    height=e.clientY-canvasTop-y;  
                      
                    $("#"+canvasId).removeLayer(layerName);  
                      
                    $("#"+canvasId).addLayer({  
                        type: 'rectangle',  
                        strokeStyle: color,  
                        strokeWidth: penWidth,  
                         name:layerName,  
                         fromCenter: false,  
                         x: x, y: y,  
                         width: width,  
                         height: height  
                    });  
                      
                    $("#"+canvasId).drawLayers();  
            }  
            }  
              
            canvas.onmouseup=function(e){  
                var color=that.penColor;  
                var penWidth=that.penWidth;  
                   
                canvas.onmousemove=null;  
                  
                width=e.clientX-canvasLeft-x;  
                height=e.clientY-canvasTop-y;  
                  
                $("#"+canvasId).removeLayer(layerName);  
                  
                $("#"+canvasId).addLayer({  
                    type: 'rectangle',  
                    strokeStyle: color,  
                    strokeWidth: penWidth,  
                     name:layerName,  
                     fromCenter: false,  
                     x: x, y: y,  
                     width: width,  
                     height: height  
                });  
                  
                $("#"+canvasId).drawLayers();  
                $("#"+canvasId).saveCanvas();  
				alert('右上角:'+ (x+width) + ',' + (y) + ';右下角:' + (x+width) + ','+  (y+height) +';左上角:'+ x +','+ y
					+ ';左下角:' + (x) + ',' + (y+height) );
				
    }  
}  
}