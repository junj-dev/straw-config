import Vue from '../../utils/vue';
import { htmlOrText } from '../../utils/html';
import { pluckProps } from '../../utils/props';
import dropdownMixin from '../../mixins/dropdown';
import idMixin from '../../mixins/id';
import normalizeSlotMixin from '../../mixins/normalize-slot';
import { props as BDropdownProps } from '../dropdown/dropdown';
import { BLink } from '../link/link'; // --- Props ---

export var props = pluckProps(['text', 'html', 'menuClass', 'toggleClass', 'noCaret', 'role', 'lazy'], BDropdownProps); // --- Main component ---
// @vue/component

export var BNavItemDropdown = /*#__PURE__*/Vue.extend({
  name: 'BNavItemDropdown',
  mixins: [idMixin, dropdownMixin, normalizeSlotMixin],
  props: props,
  computed: {
    toggleId: function toggleId() {
      return this.safeId('_BV_toggle_');
    },
    isNav: function isNav() {
      // Signal to dropdown mixin that we are in a navbar
      return true;
    },
    dropdownClasses: function dropdownClasses() {
      return [this.directionClass, {
        show: this.visible
      }];
    },
    menuClasses: function menuClasses() {
      return [this.menuClass, {
        'dropdown-menu-right': this.right,
        show: this.visible
      }];
    },
    toggleClasses: function toggleClasses() {
      return [this.toggleClass, {
        'dropdown-toggle-no-caret': this.noCaret
      }];
    }
  },
  render: function render(h) {
    var toggleId = this.toggleId,
        visible = this.visible;
    var $toggle = h(BLink, {
      staticClass: 'nav-link dropdown-toggle',
      class: this.toggleClasses,
      props: {
        href: "#".concat(this.id || ''),
        disabled: this.disabled
      },
      attrs: {
        id: toggleId,
        role: 'button',
        'aria-haspopup': 'true',
        'aria-expanded': visible ? 'true' : 'false'
      },
      on: {
        mousedown: this.onMousedown,
        click: this.toggle,
        keydown: this.toggle // Handle ENTER, SPACE and DOWN

      },
      ref: 'toggle'
    }, [// TODO: The `text` slot is deprecated in favor of the `button-content` slot
    this.normalizeSlot(['button-content', 'text']) || h('span', {
      domProps: htmlOrText(this.html, this.text)
    })]);
    var $menu = h('ul', {
      staticClass: 'dropdown-menu',
      class: this.menuClasses,
      attrs: {
        tabindex: '-1',
        'aria-labelledby': toggleId
      },
      on: {
        keydown: this.onKeydown // Handle UP, DOWN and ESC

      },
      ref: 'menu'
    }, !this.lazy || visible ? this.normalizeSlot('default', {
      hide: this.hide
    }) : [h()]);
    return h('li', {
      staticClass: 'nav-item b-nav-dropdown dropdown',
      class: this.dropdownClasses,
      attrs: {
        id: this.safeId()
      }
    }, [$toggle, $menu]);
  }
});