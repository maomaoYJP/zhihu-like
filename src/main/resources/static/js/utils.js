deBounce = function(fun,timer,wait){
    if(timer){
        clearTimeout(timer)
    }
    timer = setTimeout(fun,wait);
    return timer;

}

refreshPage = function () {
    console.log("刷新页面")
}

pullUpLoad = function (){
    let start_pos = 0,	// 记录开始的位置
        move_distance = 0, // 记录移动距离变量的值
        timer = null;
    // 添加事件
    document.addEventListener('touchstart', (e) => {
        //记录开始滑动的位置
        start_pos = e.touches[0].pageY;
    }, false);
    // 添加按住移动的事件
    document.addEventListener('touchmove', (e) => {
        // 更新记录移动距离变量的值
        move_distance = e.touches[0].pageY - start_pos;
        if(move_distance > 100) {
            // 执行回调函数,防抖
            timer = deBounce(refreshPage,timer,1000);
        }
    }, false);
}