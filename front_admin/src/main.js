import App from './App.svelte';

const app = new App({
	target: document.body,
	props: {
		url: 'https://giveyouup.gamerkids.dk:8443/access',
		xtoken: 'yN4OTJLydDsSHYQVnnkJyN4OTJLydDsSHYQVnnkJ'
	}
});

export default app;