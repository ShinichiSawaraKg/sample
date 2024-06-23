$(function () {
	//定義
	var $firstChild = $('nav ul li:first-child');
	var $secondChild = $('nav ul li:nth-child(2)');
	var $thirdChild = $('nav ul li:nth-child(3)');
	var $fourthChild = $('nav ul li:nth-child(4)');

	// ドキュメントのロード時にアンダーラインの初期状態を作成する関数を定義する
	function initialState() {
		$('.underline').css({
			"width": $firstChild.width(),
			"left": $firstChild.position().left,
			"top": $firstChild.position().top + $firstChild.outerHeight(true) + 'px'
		});
	}
	initialState();

	//どのliがアクティブかによって下線を変更する関数の定義
	function changeUnderline(el) {
		$('.underline').css({
			"width": el.width(),
			"left": el.position().left,
			"top": el.position().top + el.outerHeight(true) + 'px'
		});
	} //この関数はすぐには呼び出していない

	$firstChild.on('click', function () {
		var el = $firstChild;
		changeUnderline(el); //elをパラメータとしてchangeUnderline関数を呼び出す
		$secondChild.removeClass('active');
		$thirdChild.removeClass('active');
		$fourthChild.removeClass('active');
		$(this).addClass('active');
	});

	$secondChild.on('click', function () {
		var el = $secondChild;
		changeUnderline(el); //elをパラメータとしてchangeUnderline関数を呼び出す
		$firstChild.removeClass('active');
		$thirdChild.removeClass('active');
		$fourthChild.removeClass('active');
		$(this).addClass('active');
	});

	$thirdChild.on('click', function () {
		var el = $thirdChild;
		changeUnderline(el); //elをパラメータとしてchangeUnderline関数を呼び出す
		$firstChild.removeClass('active');
		$secondChild.removeClass('active');
		$fourthChild.removeClass('active');
		$(this).addClass('active');
	});
	
	$fourthChild.on('click', function () {
		var el = $fourthChild;
		changeUnderline(el); //elをパラメータとしてchangeUnderline関数を呼び出す
		$firstChild.removeClass('active');
		$secondChild.removeClass('active');
		$thirdChild.removeClass('active');
		$(this).addClass('active');
	});

});