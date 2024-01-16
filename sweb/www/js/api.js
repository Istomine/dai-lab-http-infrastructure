async function fetchAllKirby(){
    const url  = "http://localhost/api/kirby/get"

    const response = await fetch(url);
    return await response.json()
}

function createCard(index, title, content, imageUrl){
    const orientation = index % 2  == 0 ? 'order-lg-first' : '';
    console.log(orientation)
    const html = `
    <div class="row gx-0 justify-content-center">
        <div class="col-lg-6"><img class="img-fluid" src="${imageUrl}" alt="..." /></div>
        <div class="col-lg-6 ${orientation}order-lg-first">
            <div class="bg-black text-center h-100 project">
                <div class="d-flex h-100">
                    <div class="project-text w-100 my-auto text-center text-lg-${orientation}">
                        <h4 class="text-white">${title}</h4>
                        <p class="mb-0 text-white-50">${content}</p>
                    </div>
                </div>
            </div>
        </div>
    </div>
    `
    const elementToAppend = document.getElementsByClassName("kirby-list")[0];
    console.log(elementToAppend)
    const newCard = document.createElement('div');
    newCard.innerHTML = html;
    elementToAppend.appendChild(newCard);
}

function editContent(divToModify, content){

    divToModify.textContent = content;
    console.log(htmlDiv)
}
const htmlDiv = document.getElementsByClassName("content")[0];

editContent(htmlDiv,"lol")

createCard(0,"lol1","22","assets/img/demo-image-01x.jpg")
createCard(1,"lol2","22","assets/img/demo-image-01x.jpg")
createCard(2,"lol3","22","assets/img/demo-image-01x.jpg")
createCard(3,"lol4","22","assets/img/demo-image-01x.jpg")