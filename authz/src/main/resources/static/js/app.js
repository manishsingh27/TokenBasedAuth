angular.module('app', [ 'ngMaterial' ]).config(
		function($mdThemingProvider) {

			$mdThemingProvider.definePalette('redTheme', {
				'50' : 'ffede9',
				'100' : 'ffd1c8',
				'200' : 'ffb3a4',
				'300' : 'ff947f',
				'400' : 'ff7d63',
				'500' : 'ff6648',
				'600' : 'ff5e41',
				'700' : 'ff5338',
				'800' : 'ff4930',
				'900' : 'ff3821',
				'A100' : 'ffffff',
				'A200' : 'ffffff',
				'A400' : 'ffd4d0',
				'A700' : 'ffbdb7',
				'contrastDefaultColor' : 'light', // whether, by default, text (contrast)
				// on this palette should be dark or light
				'contrastDarkColors' : [ '50', '100', //hues which contrast should be 'dark' by default
				'200', '300', '400', 'A100' ],
				'contrastLightColors' : undefined
			});
			$mdThemingProvider.definePalette('toolbar', {
				'50' : 'ffffff',
				'100' : 'ffffff',
				'200' : 'ffffff',
				'300' : 'ffffff',
				'400' : 'ffffff',
				'500' : 'ffffff',
				'600' : 'ffffff',
				'700' : 'ffffff',
				'800' : 'ffffff',
				'900' : 'ffffff',
				'A100' : 'ffffff',
				'A200' : 'ffffff',
				'A400' : 'ffffff',
				'A700' : 'ffffff',
				'contrastDefaultColor' : 'light',
				'contrastDarkColors' : [ '50', '100', '200', '300', '400',
						'500', '600', '700', '800', '900', 'A100', 'A200',
						'A400', 'A700' ],
				'contrastLightColors' : []
			});
			$mdThemingProvider.theme('default').primaryPalette('blue').accentPalette('green');;

			$mdThemingProvider.theme('toolbar').primaryPalette('toolbar').accentPalette('green');;

		});