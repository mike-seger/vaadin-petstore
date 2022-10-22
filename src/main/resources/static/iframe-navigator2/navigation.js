function navigate(anchor) {
    const main = document.getElementById("main")
    const iframes = Array.from(main.querySelectorAll("iframe"))
    const matchingIframes = iframes.filter(iframe => iframe.name === anchor.href)
    let iframe
    if(matchingIframes.length == 0) {
        iframe = document.createElement('iframe')
        iframe.src = anchor.href
        iframe.name = anchor.href
        iframe.style.display = 'none'
        main.appendChild(iframe)
    } else iframe = matchingIframes[0]

    iframes.forEach(iframe => { iframe.style.display = "none" })
    iframe.style.display = 'block'

    const sidenav = document.getElementById("sidenav")
    const anchors = Array.from(sidenav.querySelectorAll("a"))
                
    anchors.forEach(anchor => { anchor.classList.remove("selected") })
    anchor.classList.add("selected")

    return false
}

function resetMain() {
    const main = document.getElementById("main")
    main.innerHTML = '';
}

window.onload = (event) => {
    const sidenav = document.getElementById("sidenav")
    const anchors = Array.from(sidenav.querySelectorAll("a"))
    anchors.forEach(anchor => {
        console.log(anchor.href)
        anchor.target = anchor.href
        anchor.onclick = function() { return navigate(this); }
    })
    navigate(anchors[0])
}