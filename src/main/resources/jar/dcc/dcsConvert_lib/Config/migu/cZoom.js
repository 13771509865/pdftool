//zoom
var zoomData = '<input id="scale" type="hidden" value="1.0">'
var appendStr = '<div id="zoom" style="position: fixed; right: 20px; bottom: 10px; width: 24px; height: 70px;">' +
    '<div style="border-radius:12px;width: 24px;height: 24px;background-color: #f2f2f2;text-align: center;font-size: 16px;cursor: pointer;box-shadow: 0 2px 2px 0 rgba(0, 0, 0, 0.14), 0 1px 5px 0 rgba(0, 0, 0, 0.12), 0 3px 1px -2px rgba(0, 0, 0, 0.2);" onclick="changeTab(1)"><img src="' + basePath + '/zoomIn.png" style="opacity: 0.5;"/></div>' +
    '<div style="border-radius:12px;width: 24px;height: 24px;background-color: #f2f2f2;margin-top: 10px;text-align: center;font-size: 16px;cursor: pointer;box-shadow: 0 2px 2px 0 rgba(0, 0, 0, 0.14), 0 1px 5px 0 rgba(0, 0, 0, 0.12), 0 3px 1px -2px rgba(0, 0, 0, 0.2);" onclick="changeTab(0)"><img src="' + basePath + '/zoomOut.png" style="opacity: 0.5;"/></div>' +
    '</div>';
var tranformOrigin = '50% 0%'
$('body').append(zoomData + appendStr);


function changeTab(type) {
    var size = parseFloat($("#scale").val());
    if(type==1 && size <= 3){
        size = size + 0.2;
    }else if(type==0 && size >= 0.4){
        size = size - 0.2;
    }
    $("#scale").val(size);
    renderPage();

}
