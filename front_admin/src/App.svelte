<script>
	import { onMount } from 'svelte'

	export let url, xtoken;

	let request = Promise.resolve([])

	let updateRequest = () => {
		request = fetch(url, {
			headers: {
				"X-Auth-Token": xtoken
			}
		}).then(data => data.json())
	}

	let prettiDate = (isodate) => {
		let date = new Date(isodate)

		let y = date.getFullYear()
		let m = date.getMonth() + 1
		let d = date.getDate()

		let H = date.getHours()
		let M = date.getMinutes()
		let S = date.getSeconds()

		return `${y}-${m}-${d}    ${H}:${M}:${S}`
	}

	onMount(updateRequest)	
</script>

<style>
	
</style>

<main>
	{#await request}
	<p>...waiting</p>
	{:then arr}
	<button on:click="{updateRequest}">Refresh</button>
	<p>Results:</p>
	<ul>
		{#each arr as item}
			<li>
				<p>ID: {item.id}</p>
				<p>IP: {item.ip}</p>
				<p>DATE: {prettiDate(item.date)}</p>
			</li>
		{/each}
	</ul>
	{:catch e}
	<p color="color: red;">
		Error in promise:
		{e.message}
	</p>
	{/await}
</main>