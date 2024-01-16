async function fetchAllKirby(){
    const url  = "https://localhost/api/kirby/get"

    const response = await fetch(url);
    return await response.json()
}

async function createKirby(e){
    e.preventDefault()
    fetchAllKirby();
    const kirbyName = document.getElementById("kirbyName").value
    const data = {
        name: kirbyName
    };
    const response = await fetch('https://localhost/api/kirby/create', {
        method: 'POST',
        body: JSON.stringify(data)
    });
    document.getElementById("kirbyName").value = ""

    window.location.hash = "projects";
    window.location.reload();
}

function createCard(index, title, hunger,swag, imageUrl){
    const orientation = index % 2  == 0 ? 'order-lg-first' : '';
    const html = `
    <div class="row gx-0 justify-content-center">
        <div class="col-lg-6"><img class="img-fluid" src="${imageUrl}" alt="..." /></div>
        <div class="col-lg-6 ${orientation}order-lg-first">
            <div class="bg-black text-center h-100 project">
                <div class="d-flex h-100">
                    <div class="project-text w-100 my-auto text-center text-lg-${orientation}">
                        <h4 class="text-white">${title}</h4>
                        <p class="mb-0 text-white-50">
                            Hunger : ${hunger}
                            Swag : ${swag}
                        </p>
                    </div>
                </div>
            </div>
        </div>
    </div>
    `
    const elementToAppend = document.getElementsByClassName("kirby-list")[0];
    const newCard = document.createElement('div');
    newCard.innerHTML = html;
    elementToAppend.appendChild(newCard);
    
}

document.getElementById("submitButton").addEventListener("click",createKirby);

const kirbys = await fetchAllKirby();
let index = 0;
kirbys.forEach(element => {
    const imageUrl = `https://localhost/api/kirby/image/${index}`;
    createCard(index,element.name,element.hunger,element.swagPoint,imageUrl)
    index++;
});