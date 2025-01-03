/**
 * On page load, read the "selectedBrandIds" cookie (JSON array of IDs)
 * and check those boxes. Then, whenever the user clicks "Filter" (submitting
 * the form), store the newly selected brandIds in the same cookie.
 * This way, after reload, the same boxes remain checked.
 */

function getCookie(name) {
    const nameEQ = name + "=";
    const ca = document.cookie.split(';');
    for (let i = 0; i < ca.length; i++) {
        let c = ca[i].trim();
        if (c.indexOf(nameEQ) === 0) {
            return c.substring(nameEQ.length, c.length);
        }
    }
    return null;
}

function setCookie(name, value, days) {
    const d = new Date();
    d.setTime(d.getTime() + (days * 24 * 60 * 60 * 1000));
    const expires = "expires=" + d.toUTCString();
    document.cookie = name + "=" + value + ";" + expires + ";path=/";
}

function deleteCookie(name) {
    document.cookie = name + "=; expires=Thu, 01 Jan 1970 00:00:00 UTC; path=/;";
}

document.addEventListener("DOMContentLoaded", () => {
    const cookieValue = getCookie("selectedBrandIds");
    if (cookieValue) {
        try {
            const savedIds = JSON.parse(cookieValue);
            savedIds.forEach(id => {
                const checkbox = document.getElementById("brand_" + id);
                if (checkbox) {
                    checkbox.checked = true;
                }
            });
        } catch (e) {
            console.error("Could not parse selectedBrandIds:", e);
        }
    }

    const brandForm = document.getElementById("brandForm");
    brandForm.addEventListener("submit", () => {
        const selected = [];
        brandForm.querySelectorAll("input[name='brandIds']:checked").forEach(chk => {
            selected.push(chk.value);
        });
        setCookie("selectedBrandIds", JSON.stringify(selected), 30);
    });
});

function clearFilters(event) {
    event.preventDefault();
    document.querySelectorAll("input[name='brandIds']").forEach(chk => {
        chk.checked = false;
    });
    deleteCookie("selectedBrandIds");
    window.location.href = "/";
}